package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.StandaloneQuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StandaloneQuestionRepository extends CrudRepository<StandaloneQuestionEntity, Long> {

    List<StandaloneQuestionEntity> findAllByStandaloneTaskId(Long taskId);

}
