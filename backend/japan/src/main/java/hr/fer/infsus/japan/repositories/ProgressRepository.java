package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.ProgressEntity;
import hr.fer.infsus.japan.domain.ids.ProgressId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProgressRepository extends CrudRepository<ProgressEntity, ProgressId> {

    List<ProgressEntity> findProgressEntitiesByUserEmail(String email);



}
