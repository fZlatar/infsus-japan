package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.LessonTaskEntity;
import hr.fer.infsus.japan.domain.entities.StandaloneTaskEntity;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.repositories.LessonTaskRepository;
import hr.fer.infsus.japan.repositories.StandaloneTaskRepository;
import hr.fer.infsus.japan.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {

    private final LessonTaskRepository lessonTaskRepository;

    private final StandaloneTaskRepository standaloneTaskRepository;

    @Override
    @Transactional(readOnly = true)
    public LessonTaskEntity findLessonTaskById(Long id) {
        return lessonTaskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public StandaloneTaskEntity findStandaloneTaskById(Long id) {
        return standaloneTaskRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<LessonTaskEntity> findLessonTasksByLessonId(Long lessonId) {
        return new HashSet<>(lessonTaskRepository.findLessonTasksByLessonId(lessonId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<StandaloneTaskEntity> findStandaloneTasks() {
        return StreamSupport.stream(standaloneTaskRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

}
