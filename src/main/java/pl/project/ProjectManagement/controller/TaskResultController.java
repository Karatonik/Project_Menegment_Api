package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.TaskResult;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/res")
@CrossOrigin(origins = "*", maxAge = 7200)
public class TaskResultController{

    private final TaskResultService taskResultService;
    private final ModelWrapper modelWrapper;

    @Autowired
    public TaskResultController(TaskResultService taskResultService,
                                ModelWrapper modelWrapper) {
        this.taskResultService = taskResultService;
        this.modelWrapper = modelWrapper;
    }

    @PostMapping
    public ResponseEntity<TaskResultDto> setTaskResult( @RequestBody TaskResultDto taskResultDto) {
        return ResponseEntity.ok(new TaskResultDto(this.taskResultService
                .setTaskResult(this.modelWrapper
                .getTaskResultFromTaskResultDto(taskResultDto))));
    }

    @GetMapping("/{resultId}/{projectOwnerEmail}")
    public ResponseEntity<TaskResultDto> getTaskResult(long resultId, String projectOwnerEmail) {
        return ResponseEntity.ok(new TaskResultDto(this.taskResultService
                .getTaskResult(resultId, projectOwnerEmail)));
    }
    @GetMapping("/task/{taskId}/{projectOwnerEmail}")
    public ResponseEntity<List<TaskResultDto>> getTaskResultsByTask(long taskId, String projectOwnerEmail) {
        return ResponseEntity.ok(this.taskResultService
                .getTaskResultsByTask(taskId,projectOwnerEmail)
                .stream().map(TaskResultDto::new).collect(Collectors.toList()));

    }
}
