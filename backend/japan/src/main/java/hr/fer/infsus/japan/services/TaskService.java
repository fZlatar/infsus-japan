package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.LessonTaskEntity;
import hr.fer.infsus.japan.domain.entities.StandaloneTaskEntity;

import java.util.Set;

public interface TaskService {

    LessonTaskEntity findLessonTaskById(Long id);

    StandaloneTaskEntity findStandaloneTaskById(Long id);

    Set<LessonTaskEntity> findLessonTasksByLessonId(Long lessonId);

    Set<StandaloneTaskEntity> findStandaloneTasks();

}
