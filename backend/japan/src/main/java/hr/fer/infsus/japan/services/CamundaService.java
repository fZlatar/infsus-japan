package hr.fer.infsus.japan.services;

import java.util.List;
import java.util.Map;

import hr.fer.infsus.japan.dtos.CamundaTaskDto;

public interface CamundaService {

    String startTestProcess(String email, Long lessonId);
    List<CamundaTaskDto> getTasksForUser(String email);
    Map<String, Boolean> completeTask(String taskId, Map<String, Object> variables);
}