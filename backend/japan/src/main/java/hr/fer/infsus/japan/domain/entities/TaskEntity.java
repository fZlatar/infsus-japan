package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "task_name_cro", nullable = false, length = 80)
    private String taskNameCro;

    @Column(name = "task_name_jpn", nullable = false, length = 80)
    private String taskNameJpn;

}
