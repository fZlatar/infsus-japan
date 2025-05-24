package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity(name = "term")
@NoArgsConstructor
@AllArgsConstructor
public class TermEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(name = "term_name_cro", nullable = false, length = 50)
    private String termNameCro;

    @Column(name = "term_name_jpn", nullable = false, length = 50)
    private String termNameJpn;

    @Column(name = "description_cro", nullable = false, length = 150)
    private String descriptionCro;

    @Column(name = "description_jpn", nullable = false, length = 150)
    private String descriptionJpn;

    @Nullable
    @Column(name = "audio_uuid")
    private UUID audioUuid;

    @Nullable
    @Column(name = "image_uuid")
    private UUID imageFile;

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonTermEntity> lessonTerms = new HashSet<>();

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StandaloneQuestionEntity> standaloneQuestions = new HashSet<>();

}
