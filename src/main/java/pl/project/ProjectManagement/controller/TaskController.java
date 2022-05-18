package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.model.request.SecoundParent.WithProjectPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", maxAge = 7200)
public class TaskController {

    private final TaskService taskService;
    private final ModelWrapper modelWrapper;

    private final InfoService infoService;

    @Autowired
    public TaskController(TaskService taskService, ModelWrapper modelWrapper, InfoService infoService) {
        this.taskService = taskService;
        this.modelWrapper = modelWrapper;
        this.infoService = infoService;
    }

    @PostMapping
    public ResponseEntity<?> setTask(@RequestBody @Valid TaskDto taskDto) {
        taskDto = new TaskDto(this.taskService.setTask(modelWrapper.getTaskFromDto(taskDto)));

        if (taskDto.equals(new TaskDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable long taskId) {
        TaskDto taskDto = new TaskDto(this.taskService.getTask(taskId));
        if (taskDto.equals(new TaskDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/project")
    public ResponseEntity<?> getProjectTasks(@RequestHeader("Authorization") String authorization,
                                             @RequestBody @Valid WithProjectPayload payload,
                                             Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.taskService
                .getProjectTasks(this.infoService.getEmailFromJwt(authorization), payload.getProjectId())
                .stream().map(TaskDto::new).collect(Collectors.toList()), pageable, size));
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<?> getTasks(@RequestHeader("Authorization") String authorization,
                                      @PathVariable String token, Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.taskService
                .getTasks(this.infoService.getEmailFromJwt(authorization), token)
                .stream().map(TaskDto::new).collect(Collectors.toList()), pageable, size));
    }
}
