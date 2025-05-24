package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.LessonQuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonQuestionRepository extends CrudRepository<LessonQuestionEntity, Long> {

    List<LessonQuestionEntity> findAllByLessonTaskId(Long taskId);

}
