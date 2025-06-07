package hr.fer.infsus.japan.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hr.fer.infsus.japan.dtos.CamundaProcessStartRequest;
import hr.fer.infsus.japan.dtos.CamundaTaskDto;
import hr.fer.infsus.japan.services.CamundaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/camunda")
@RequiredArgsConstructor
public class CamundaProcessController {
    
    private final CamundaService camundaService;

    @PostMapping("/start-test-process")
    public ResponseEntity<String> startTestProcess(@RequestBody CamundaProcessStartRequest request) {
        String instanceId = camundaService.startTestProcess(request.email(), request.lessonId());
        return ResponseEntity.ok(instanceId);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<CamundaTaskDto>> getTasksForUser(@RequestParam String email) {
        List<CamundaTaskDto> tasks = camundaService.getTasksForUser(email);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/complete-task/{taskId}")
    public ResponseEntity<Map<String, Boolean>> completeTask(
            @PathVariable String taskId, 
            @RequestBody Map<String, Object> variables) {
        if (!variables.containsKey("correctAnswers")){
            return ResponseEntity.badRequest().build();
        }
        Map<String, Boolean> result = camundaService.completeTask(taskId, variables);
        return ResponseEntity.ok(result);

    }
}
