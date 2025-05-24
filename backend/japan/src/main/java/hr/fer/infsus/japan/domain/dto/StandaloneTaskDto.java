package hr.fer.infsus.japan.domain.dto;

import lombok.Data;

@Data
public class StandaloneTaskDto {

    private Long id;

    private String taskNameCro;

    private String taskNameJpn;

    private Integer difficulty;

}
