package jam.workspace.springdatajpa;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String LastName;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;
    @Column(name = "age", nullable = false)
    private Integer age;
    @OneToOne(mappedBy = "student", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {PERSIST, REMOVE})
    private StudentIdCard studentIdCard;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", orphanRemoval = true, cascade = {PERSIST, REMOVE})
    private List<Book> books = new ArrayList<>();
    @OneToMany(
            cascade = {PERSIST, REMOVE},
            mappedBy = "student"
    )
    private List<Enrolment> enrolments = new ArrayList<>();

    public void addBook(Book book) {
        if (!this.books.contains(book)) {
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
    }
    public void addEnrolment(Enrolment enrolment) {
        if (!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
            enrolment.setStudent(this);
        }
    }
    public void removeEnrolment(Enrolment enrolment) {
        this.enrolments.remove(enrolment);
        enrolment.setStudent(null);
    }

    public Student(String firstName, String LastName, String email, Integer age) {
        this.firstName = firstName;
        this.LastName = LastName;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", studentIdCard=" + studentIdCard +
                '}';
    }
}
