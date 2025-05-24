package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.LessonTermEntity;
import hr.fer.infsus.japan.domain.entities.TermEntity;
import hr.fer.infsus.japan.domain.ids.LessonTermId;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.repositories.LessonTermRepository;
import hr.fer.infsus.japan.repositories.TermRepository;
import hr.fer.infsus.japan.services.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;

    private final LessonTermRepository lessonTermRepository;

    @Override
    @Transactional(readOnly = true)
    public TermEntity findTermByIdThrow(Long id) {
        return termRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonTermEntity findLessonTermByIdThrow(Long termId, Long lessonId) {
        return lessonTermRepository.findById(new LessonTermId(termId, lessonId)).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TermEntity> findTerms() {
        return StreamSupport.stream(termRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<LessonTermEntity> findLessonTermsByLessonId(Long lessonId) {
        return new HashSet<>(lessonTermRepository.findLessonTermsByLessonId(lessonId));
    }

}
