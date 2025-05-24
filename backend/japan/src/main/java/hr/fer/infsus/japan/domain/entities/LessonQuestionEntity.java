package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "lesson_question")
@PrimaryKeyJoinColumn(name = "id")
public class LessonQuestionEntity extends QuestionEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private LessonTaskEntity lessonTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "term_id", referencedColumnName = "term_id", nullable = false),
            @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id", nullable = false)
    })
    private LessonTermEntity lessonTerm;

}
