package jam.workspace.springdatajpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Course")
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "course_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "course_name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "department", nullable = false, columnDefinition = "TEXT")
    private String department;
    @OneToMany(mappedBy = "course", cascade = {PERSIST, REMOVE})
    private List<Enrolment> enrolments = new ArrayList<>();

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }
    public void addEnrolment(Enrolment enrolment) {
        if (!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
            enrolment.setCourse(this);
        }
    }
    public void removeEnrolment(Enrolment enrolment) {
        this.enrolments.remove(enrolment);
        enrolment.setCourse(null);
    }
}
