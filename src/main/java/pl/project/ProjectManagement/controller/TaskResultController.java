package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import javax.validation.Valid;
import java.util.List;

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
        return ResponseEntity.ok(new TaskResultDto(this.taskResultService
                .setTaskResult(this.modelWrapper.getTaskResultFromTaskResultDto(taskResultDto))));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskResult(@RequestHeader("Authorization") String authorization,
                                           @PathVariable long taskId) {
        return ResponseEntity.ok(new TaskResultDto(this.taskResultService
                .getTaskResult(taskId, this.infoService.getEmailFromJwt(authorization))));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getTaskResultsByTask(@RequestHeader("Authorization") String authorization,
                                                  @PathVariable long taskId,
                                                  Pageable pageable) {
        List<TaskResultDto> taskResultDtoList = taskResultService
                .getTaskResultsByTask(taskId, this.infoService.getEmailFromJwt(authorization), pageable)
                .stream().map(TaskResultDto::new).toList();

        return ResponseEntity.ok(new PageImpl<>(taskResultDtoList, pageable, taskResultDtoList.size()));

    }
}
