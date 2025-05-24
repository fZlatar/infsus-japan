package hr.fer.infsus.japan.domain.dto;

import lombok.Data;

@Data
public class LessonQuestionDto {

    private Long id;

    private String questionCro;

    private String questionJpn;

    private String hintCro;

    private String hintJpn;

    private String explanationCro;

    private String explanationJpn;

    private LessonTaskDto lessonTask;

    private LessonTermDto lessonTerm;

}
