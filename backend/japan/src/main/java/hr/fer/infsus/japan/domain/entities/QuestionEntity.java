package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity(name = "question")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "question_cro", nullable = false, length = 80)
    private String questionCro;

    @Column(name = "question_jpn", nullable = false, length = 80)
    private String questionJpn;

    @Column(name = "hint_cro", nullable = false, length = 80)
    private String hintCro;

    @Column(name = "hint_jpn", nullable = false, length = 80)
    private String hintJpn;

    @Column(name = "explanation_cro", nullable = false, length = 150)
    private String explanationCro;

    @Column(name = "explanation_jpn", nullable = false, length = 150)
    private String explanationJpn;

}
