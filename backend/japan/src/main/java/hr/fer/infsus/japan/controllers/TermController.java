package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.dto.LessonTermDto;
import hr.fer.infsus.japan.domain.dto.TermDto;
import hr.fer.infsus.japan.domain.entities.LessonTermEntity;
import hr.fer.infsus.japan.domain.entities.TermEntity;
import hr.fer.infsus.japan.mappers.impl.LessonTermMapper;
import hr.fer.infsus.japan.mappers.impl.TermMapper;
import hr.fer.infsus.japan.services.TermService;
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
@RequestMapping("/terms")
public class TermController {

    private final TermMapper termMapper;

    private final LessonTermMapper lessonTermMapper;

    private final TermService termService;

    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<TermDto> getTerm(@PathVariable("id") Long id) {
        return ResponseEntity.ok(termMapper.toDto(termService.findTermByIdThrow(id)));
    }

    @PermitAll
    @GetMapping("/lesson/{lessonId}/{termId}")
    public ResponseEntity<LessonTermDto> getLessonTerm(
            @PathVariable("termId") Long termId,
            @PathVariable("lessonId") Long lessonId
    ) {
        return ResponseEntity.ok(lessonTermMapper.toDto(termService.findLessonTermByIdThrow(termId, lessonId)));
    }

    @PermitAll
    @GetMapping
    public ResponseEntity<Set<TermDto>> getTerms() {
        Set<TermEntity> terms = termService.findTerms();
        return ResponseEntity.ok(terms.stream().map(termMapper::toDto).collect(Collectors.toSet()));
    }

    @PermitAll
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Set<LessonTermDto>> getLessonTerms(
            @PathVariable("lessonId") Long lessonId
    ) {
        Set<LessonTermEntity> terms = termService.findLessonTermsByLessonId(lessonId);
        return ResponseEntity.ok(terms.stream().map(lessonTermMapper::toDto).collect(Collectors.toSet()));
    }


}
