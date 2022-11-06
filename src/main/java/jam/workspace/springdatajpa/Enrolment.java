package jam.workspace.springdatajpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "enrolment_student_id_fk"))
    private Student student;
    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "enrolment_course_id_fk"))
    private Course course;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
