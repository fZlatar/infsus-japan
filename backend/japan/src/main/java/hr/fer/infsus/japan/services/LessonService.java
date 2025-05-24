package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.LessonEntity;

import java.util.Set;

public interface LessonService {

    LessonEntity findByIdThrow(Long id);

    Set<LessonEntity> findAll();

}
