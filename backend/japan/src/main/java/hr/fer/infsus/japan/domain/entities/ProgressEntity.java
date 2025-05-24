package hr.fer.infsus.japan.domain.entities;

import hr.fer.infsus.japan.domain.ids.ProgressId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@Entity(name = "progress")
@IdClass(ProgressId.class)
@NoArgsConstructor
@AllArgsConstructor
public class ProgressEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, columnDefinition = "VARCHAR(320)")
    private UserEntity user;

    @Column(name = "term_num", nullable = false)
    private int termNum;

    @Nullable
    @Column(name = "question_num")
    private Integer questionNum;

}
