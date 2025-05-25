package hr.fer.infsus.japan.domain.dto;

import lombok.Data;

@Data
public class ProgressDto {

    private LessonDto lesson;

    private UserDto user;

    private int termNum;

    private Integer questionNum;

}
