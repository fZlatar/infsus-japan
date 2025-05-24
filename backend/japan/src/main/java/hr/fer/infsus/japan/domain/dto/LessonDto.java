package hr.fer.infsus.japan.domain.dto;

import lombok.Data;

@Data
public class LessonDto {

    private Long id;

    private String lessonNameCro;

    private String lessonNameJpn;

    private String descriptionCro;

    private String descriptionJpn;

    private Integer difficulty;

}
