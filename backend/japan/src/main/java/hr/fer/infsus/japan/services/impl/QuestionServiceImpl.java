package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.LessonQuestionEntity;
import hr.fer.infsus.japan.domain.entities.StandaloneQuestionEntity;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.repositories.LessonQuestionRepository;
import hr.fer.infsus.japan.repositories.StandaloneQuestionRepository;
import hr.fer.infsus.japan.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final LessonQuestionRepository lessonQuestionRepository;

    private final StandaloneQuestionRepository standaloneQuestionRepository;

    @Override
    @Transactional(readOnly = true)
    public LessonQuestionEntity findLessonQuestionByIdThrow(Long id) {
        return lessonQuestionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public StandaloneQuestionEntity findStandaloneQuestionByIdThrow(Long id) {
        return standaloneQuestionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<LessonQuestionEntity> findAllLessonQuestionsByTask(Long taskId) {
        return new HashSet<>(lessonQuestionRepository.findAllByLessonTaskId(taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<StandaloneQuestionEntity> findAllStandaloneQuestionsByTask(Long taskId) {
        return new HashSet<>(standaloneQuestionRepository.findAllByStandaloneTaskId(taskId));
    }

}
