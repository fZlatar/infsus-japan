package hr.fer.infsus.japan.domain.entities;

import hr.fer.infsus.japan.domain.ids.LessonTermId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "lesson_term")
@IdClass(LessonTermId.class)
public class LessonTermEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "term_id", nullable = false)
    private TermEntity term;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    @Column(name = "term_num", nullable = false)
    private Integer termNum;

    @OneToMany(mappedBy = "lessonTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonQuestionEntity> lessonQuestions;

}
