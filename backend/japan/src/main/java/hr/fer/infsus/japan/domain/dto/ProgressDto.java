package hr.fer.infsus.japan.domain.dto;

import hr.fer.infsus.japan.domain.entities.LessonEntity;
import hr.fer.infsus.japan.domain.entities.UserEntity;
import lombok.Data;

@Data
public class ProgressDto {

    private LessonEntity lesson;

    private UserEntity user;

    private int termNum;

    private Integer questionNum;

}
