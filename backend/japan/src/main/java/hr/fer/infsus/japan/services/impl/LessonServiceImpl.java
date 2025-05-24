package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.LessonEntity;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.repositories.LessonRepository;
import hr.fer.infsus.japan.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Transactional(readOnly = true)
    @Override
    public Set<LessonEntity> findAll() {
        return StreamSupport.stream(lessonRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public LessonEntity findByIdThrow(Long id) {
        return lessonRepository.findById(id).orElseThrow(NotFoundException::new);
    }

}
