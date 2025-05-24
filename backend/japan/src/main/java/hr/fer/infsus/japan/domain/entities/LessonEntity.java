package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity(name = "lesson")
@NoArgsConstructor
@AllArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "lesson_name_cro", nullable = false, length = 50)
    private String lessonNameCro;

    @Column(name = "lesson_name_jpn", nullable = false, length = 50)
    private String lessonNameJpn;

    @Column(name = "description_cro", nullable = false, length = 200)
    private String descriptionCro;

    @Column(name = "description_jpn", nullable = false, length = 200)
    private String descriptionJpn;

    @Column(nullable = false)
    private Integer difficulty;
    
    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonTaskEntity> lessonTasks = new HashSet<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProgressEntity> progresses = new HashSet<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonTermEntity> lessonTerms = new HashSet<>();

}
