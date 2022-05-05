package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.model.request.ResultAccessPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/res")
@CrossOrigin(origins = "*", maxAge = 7200)
public class TaskResultController {

    private final TaskResultService taskResultService;
    private final ModelWrapper modelWrapper;

    @Autowired
    public TaskResultController(TaskResultService taskResultService,
                                ModelWrapper modelWrapper) {
        this.taskResultService = taskResultService;
        this.modelWrapper = modelWrapper;
    }

    @PostMapping
    public ResponseEntity<?> setTaskResult(@RequestBody @Valid TaskResultDto taskResultDto) {
        taskResultDto = new TaskResultDto(this.taskResultService
                .setTaskResult(this.modelWrapper.getTaskResultFromTaskResultDto(taskResultDto)));
        if (taskResultDto.equals(new TaskResultDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskResultDto);
    }

    @GetMapping
    public ResponseEntity<?> getTaskResult(@RequestBody @Valid ResultAccessPayload payload) {
        TaskResultDto taskResultDto = new TaskResultDto(this.taskResultService
                .getTaskResult(payload.getTaskId(), payload.getProjectOwnerEmail()));
        if (taskResultDto.equals(new TaskResultDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskResultDto);

    }

    @GetMapping("/task")
    public ResponseEntity<?> getTaskResultsByTask(@RequestBody @Valid ResultAccessPayload payload,
                                                  Pageable pageable, long size) {
        return ResponseEntity.ok(new PageImpl<>(this.taskResultService
                .getTaskResultsByTask(payload.getTaskId(), payload.getProjectOwnerEmail())
                .stream().map(TaskResultDto::new).collect(Collectors.toList()), pageable, size));

    }
}
