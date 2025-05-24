package hr.fer.infsus.japan.domain.dto;

import lombok.Data;

@Data
public class LessonTermDto {

    private TermDto term;

    private LessonDto lesson;

    private Integer termNum;

}
