package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "standalone_task")
@PrimaryKeyJoinColumn(name = "id")
public class StandaloneTaskEntity extends TaskEntity {

    @Column(nullable = false)
    private Integer difficulty;

    @OneToMany(mappedBy = "standaloneTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StandaloneQuestionEntity> standaloneQuestions = new HashSet<>();

}
