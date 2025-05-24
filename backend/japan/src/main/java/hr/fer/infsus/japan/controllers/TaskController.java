package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.dto.LessonTaskDto;
import hr.fer.infsus.japan.domain.dto.StandaloneTaskDto;
import hr.fer.infsus.japan.domain.entities.LessonTaskEntity;
import hr.fer.infsus.japan.domain.entities.StandaloneTaskEntity;
import hr.fer.infsus.japan.mappers.impl.LessonTaskMapper;
import hr.fer.infsus.japan.mappers.impl.StandaloneTaskMapper;
import hr.fer.infsus.japan.services.TaskService;
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
@RequestMapping("/tasks")
public class TaskController {

    private final LessonTaskMapper lessonTaskMapper;

    private final StandaloneTaskMapper standaloneTaskMapper;

    private final TaskService taskService;

    @PermitAll
    @GetMapping("/standalone/{id}")
    public ResponseEntity<StandaloneTaskDto> getStandaloneTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                standaloneTaskMapper.toDto(taskService.findStandaloneTaskById(id))
        );
    }

    @PermitAll
    @GetMapping("/lesson/{id}")
    public ResponseEntity<LessonTaskDto> getLessonTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                lessonTaskMapper.toDto(taskService.findLessonTaskById(id))
        );
    }

    @PermitAll
    @GetMapping("/lesson/all/{lessonId}")
    public ResponseEntity<Set<LessonTaskDto>> getLessonTasks(@PathVariable("lessonId") Long lessonId) {
        Set<LessonTaskEntity> tasks = taskService.findLessonTasksByLessonId(lessonId);
        return ResponseEntity.ok(
                tasks.stream().map(lessonTaskMapper::toDto).collect(Collectors.toSet())
        );
    }

    @PermitAll
    @GetMapping("/standalone/all")
    public ResponseEntity<Set<StandaloneTaskDto>> getStandaloneTasks() {
        Set<StandaloneTaskEntity> tasks = taskService.findStandaloneTasks();
        return ResponseEntity.ok(
                tasks.stream().map(standaloneTaskMapper::toDto).collect(Collectors.toSet())
        );
    }

}
