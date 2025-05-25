package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.LessonEntity;
import hr.fer.infsus.japan.domain.entities.ProgressEntity;
import hr.fer.infsus.japan.domain.entities.UserEntity;
import hr.fer.infsus.japan.domain.ids.ProgressId;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.repositories.ProgressRepository;
import hr.fer.infsus.japan.services.LessonService;
import hr.fer.infsus.japan.services.ProgressService;
import hr.fer.infsus.japan.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;

    private final LessonService lessonService;

    private final UserService userService;

    public ProgressServiceImpl(
            final ProgressRepository progressRepository,
            @Lazy final LessonService lessonService,
            @Lazy final UserService userService
    ) {
        this.progressRepository = progressRepository;
        this.lessonService = lessonService;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgressEntity> findById(Long lessonId, String email) {
        return progressRepository.findById(new ProgressId(lessonId, email));
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressEntity findByIdThrow(Long lessonId, String email) {
        return progressRepository.findById(new ProgressId(lessonId, email)).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ProgressEntity> findAllProgressesByUser(String email) {
        return new HashSet<>(progressRepository.findProgressEntitiesByUserEmail(email));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProgressEntity updateProgress(
            String email,
            Long lessonId,
            Integer termNum,
            Integer questionNum
    ) {
        ProgressEntity progressEntity = findById(lessonId, email).orElse(null);

        if (progressEntity == null) {
            UserEntity user = userService.findByEmailThrow(email);
            LessonEntity lesson = lessonService.findByIdThrow(lessonId);
            progressEntity = ProgressEntity.builder()
                    .user(user)
                    .lesson(lesson)
                    .build();
        }

        progressEntity.setTermNum(termNum);
        progressEntity.setQuestionNum(questionNum);

        return progressRepository.save(progressEntity);
    }

}
