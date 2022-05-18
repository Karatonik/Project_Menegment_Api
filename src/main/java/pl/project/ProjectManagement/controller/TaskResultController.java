package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.InfoService;
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

    private final InfoService infoService;

    @Autowired
    public TaskResultController(TaskResultService taskResultService,
                                ModelWrapper modelWrapper, InfoService infoService) {
        this.taskResultService = taskResultService;
        this.modelWrapper = modelWrapper;
        this.infoService = infoService;
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

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskResult(@RequestHeader("Authorization") String authorization,
                                           @PathVariable long taskId) {
        TaskResultDto taskResultDto = new TaskResultDto(this.taskResultService
                .getTaskResult(taskId, this.infoService.getEmailFromJwt(authorization)));
        if (taskResultDto.equals(new TaskResultDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskResultDto);

    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getTaskResultsByTask(@RequestHeader("Authorization") String authorization,
                                                  @PathVariable long taskId,
                                                  Pageable pageable, long size) {
        return ResponseEntity.ok(new PageImpl<>(this.taskResultService
                .getTaskResultsByTask(taskId, this.infoService.getEmailFromJwt(authorization))
                .stream().map(TaskResultDto::new).collect(Collectors.toList()), pageable, size));

    }
}
