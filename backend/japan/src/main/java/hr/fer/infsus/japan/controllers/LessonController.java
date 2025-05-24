package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.entities.LessonEntity;
import hr.fer.infsus.japan.domain.dto.LessonDto;
import hr.fer.infsus.japan.mappers.impl.LessonMapper;
import hr.fer.infsus.japan.services.LessonService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    private final LessonMapper lessonMapper;

    @PermitAll
    @GetMapping
    public ResponseEntity<Set<LessonDto>> getLessons() {
        Set<LessonEntity> lessons = lessonService.findAll();

        return ResponseEntity.ok(lessons.stream().map(lessonMapper::toDto).collect(Collectors.toSet()));
    }

    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLesson(@PathVariable("id") Long id) {
        LessonEntity lesson = lessonService.findByIdThrow(id);

        return ResponseEntity.ok(lessonMapper.toDto(lesson));
    }

}
