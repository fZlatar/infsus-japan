package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.ProgressEntity;

import java.util.Optional;
import java.util.Set;

public interface ProgressService {

    Optional<ProgressEntity> findById(Long lessonId, String email);

    ProgressEntity findByIdThrow(Long lessonId, String email);

    Set<ProgressEntity> findAllProgressesByUser(String email);

    ProgressEntity updateProgress(
            String email,
            Long lessonId,
            Integer termNum,
            Integer questionNum
    );

}
