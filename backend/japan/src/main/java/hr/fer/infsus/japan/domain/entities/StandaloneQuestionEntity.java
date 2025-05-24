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
@Entity(name = "standalone_question")
@PrimaryKeyJoinColumn(name = "id")
public class StandaloneQuestionEntity extends QuestionEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private StandaloneTaskEntity standaloneTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "term_id")
    private TermEntity term;

}
