package hr.fer.infsus.japan.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
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
    private final HistoryService historyService;

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
    public List<CamundaTaskDto> getTasksForUser(String email) {
        return taskService.createTaskQuery()
            .taskAssignee(email)
            .list()
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    @Override
    public Map<String, Boolean> completeTask(String taskId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery()
            .taskId(taskId)
            .singleResult();

        String processInstanceId = task.getProcessInstanceId();
        taskService.complete(taskId, variables);

        HistoricVariableInstance passedVar = historyService
            .createHistoricVariableInstanceQuery()
            .processInstanceId(processInstanceId)
            .variableName("passed")
            .singleResult();
        boolean passed = passedVar != null ? (boolean) passedVar.getValue() : false;

        return Map.of("passed", passed);
    }

    private CamundaTaskDto convertToDto(Task task) {
        return new CamundaTaskDto(
            task.getId(),
            task.getName(),
            task.getProcessInstanceId()
        );
    }
}
