package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.LessonQuestionEntity;
import hr.fer.infsus.japan.domain.entities.StandaloneQuestionEntity;

import java.util.Set;

public interface QuestionService {

    LessonQuestionEntity findLessonQuestionByIdThrow(Long id);

    StandaloneQuestionEntity findStandaloneQuestionByIdThrow(Long id);

    Set<LessonQuestionEntity> findAllLessonQuestionsByTask(Long taskId);

    Set<StandaloneQuestionEntity> findAllStandaloneQuestionsByTask(Long taskId);

}
