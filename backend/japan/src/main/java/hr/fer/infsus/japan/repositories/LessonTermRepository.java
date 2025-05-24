package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.LessonTermEntity;
import hr.fer.infsus.japan.domain.ids.LessonTermId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonTermRepository extends CrudRepository<LessonTermEntity, LessonTermId> {

    List<LessonTermEntity> findLessonTermsByLessonId(Long lessonId);

}
