package jam.workspace.springdatajpa;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnrolmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "course_id")
    private Long courseId;


}
