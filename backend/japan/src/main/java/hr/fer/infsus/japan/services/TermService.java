package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.LessonTermEntity;
import hr.fer.infsus.japan.domain.entities.TermEntity;

import java.util.Set;

public interface TermService {

    TermEntity findTermByIdThrow(Long id);

    LessonTermEntity findLessonTermByIdThrow(Long termId, Long lessonId);

    Set<TermEntity> findTerms();

    Set<LessonTermEntity> findLessonTermsByLessonId(Long lessonId);

}
