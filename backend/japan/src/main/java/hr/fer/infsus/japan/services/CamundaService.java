package hr.fer.infsus.japan.services;

import java.util.List;
import java.util.Map;

import hr.fer.infsus.japan.dtos.CamundaTaskDto;

public interface CamundaService {

    String startTestProcess(String userEmail, Long lessonId);
    List<CamundaTaskDto> getTasksForUser(String userEmail);
    void completeTask(String taskId, Map<String, Object> variables);
}