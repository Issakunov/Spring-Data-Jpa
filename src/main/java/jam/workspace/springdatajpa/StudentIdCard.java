package jam.workspace.springdatajpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;

@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
uniqueConstraints = {
                @UniqueConstraint(name = "student_id_card_unique", columnNames = {"card_number"})
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_id_card_sequence",
            sequenceName = "id_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "id_card_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "card_number", nullable = false, length = 15)
    private String cardNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "student_id_fk"))
    @ToString.Exclude
    private Student student;

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }
}
