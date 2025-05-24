package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.dto.LessonQuestionDto;
import hr.fer.infsus.japan.domain.dto.StandaloneQuestionDto;
import hr.fer.infsus.japan.domain.entities.LessonQuestionEntity;
import hr.fer.infsus.japan.domain.entities.StandaloneQuestionEntity;
import hr.fer.infsus.japan.mappers.impl.LessonQuestionMapper;
import hr.fer.infsus.japan.mappers.impl.StandaloneQuestionMapper;
import hr.fer.infsus.japan.services.QuestionService;
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
@RequestMapping("/questions")
public class QuestionController {

    private final StandaloneQuestionMapper standaloneQuestionMapper;

    private final LessonQuestionMapper lessonQuestionMapper;

    private final QuestionService questionService;

    @PermitAll
    @GetMapping("/standalone/all/{taskId}")
    public ResponseEntity<Set<StandaloneQuestionDto>> getAllStandaloneQuestions(@PathVariable("taskId") Long taskId) {
        Set<StandaloneQuestionEntity> questions = questionService.findAllStandaloneQuestionsByTask(taskId);
        return ResponseEntity.ok(questions.stream().map(standaloneQuestionMapper::toDto).collect(Collectors.toSet()));
    }

    @PermitAll
    @GetMapping("/lesson/all/{taskId}")
    public ResponseEntity<Set<LessonQuestionDto>> getAllLessonQuestions(@PathVariable("taskId") Long taskId) {
        Set<LessonQuestionEntity> questions = questionService.findAllLessonQuestionsByTask(taskId);
        return ResponseEntity.ok(questions.stream().map(lessonQuestionMapper::toDto).collect(Collectors.toSet()));
    }

    @PermitAll
    @GetMapping("/lesson/{id}")
    public ResponseEntity<LessonQuestionDto> getLessonQuestion(@PathVariable("id") Long id) {
        LessonQuestionEntity question = questionService.findLessonQuestionByIdThrow(id);
        return ResponseEntity.ok(lessonQuestionMapper.toDto(question));
    }

    @PermitAll
    @GetMapping("/standalone/{id}")
    public ResponseEntity<StandaloneQuestionDto> getStandaloneQuestion(@PathVariable("id") Long id) {
        StandaloneQuestionEntity question = questionService.findStandaloneQuestionByIdThrow(id);
        return ResponseEntity.ok(standaloneQuestionMapper.toDto(question));
    }

}
