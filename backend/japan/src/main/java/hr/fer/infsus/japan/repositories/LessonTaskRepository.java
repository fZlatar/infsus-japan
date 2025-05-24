package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.LessonTaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonTaskRepository extends CrudRepository<LessonTaskEntity, Long> {

    List<LessonTaskEntity> findLessonTasksByLessonId(Long lessonId);

}
