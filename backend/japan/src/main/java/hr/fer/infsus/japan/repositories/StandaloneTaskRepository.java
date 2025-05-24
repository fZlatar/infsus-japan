package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.StandaloneTaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface StandaloneTaskRepository extends CrudRepository<StandaloneTaskEntity, Long> {
}
