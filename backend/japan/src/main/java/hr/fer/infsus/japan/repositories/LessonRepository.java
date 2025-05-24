package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.LessonEntity;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<LessonEntity, Long> {
}
