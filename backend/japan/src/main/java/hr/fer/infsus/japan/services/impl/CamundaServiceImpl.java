package hr.fer.infsus.japan.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import hr.fer.infsus.japan.dtos.CamundaTaskDto;
import hr.fer.infsus.japan.services.CamundaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CamundaServiceImpl implements CamundaService{
    
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Override
    public String startTestProcess(String email, Long lessonId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("email", email);
        variables.put("lessonId", lessonId);
        
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(
            "Test-process-12", 
            variables
        );

        return instance.getProcessDefinitionId();
    }

    @Override
    public List<CamundaTaskDto> getTasksForUser(String userEmail) {
        return taskService.createTaskQuery()
            .taskAssignee(userEmail)
            .list()
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    private CamundaTaskDto convertToDto(Task task) {
        return new CamundaTaskDto(
            task.getId(),
            task.getName(),
            task.getProcessInstanceId()
        );
    }
}
