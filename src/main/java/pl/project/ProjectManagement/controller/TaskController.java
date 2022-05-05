package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.model.request.Parent.AdminAccessPayload;
import pl.project.ProjectManagement.model.request.Parent.TaskPayload;
import pl.project.ProjectManagement.model.request.SecoundParent.WithProjectPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
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

    @Autowired
    public TaskController(TaskService taskService, ModelWrapper modelWrapper) {
        this.taskService = taskService;
        this.modelWrapper = modelWrapper;
    }

    @PostMapping
    public ResponseEntity<?> setTask(@RequestBody @Valid TaskDto taskDto) {
        taskDto = new TaskDto(this.taskService.setTask(modelWrapper.getTaskFromDto(taskDto)));

        if (taskDto.equals(new TaskDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping
    public ResponseEntity<?> getTask(@RequestBody @Valid TaskPayload payload) {
        TaskDto taskDto = new TaskDto(this.taskService.getTask(payload.getTaskId()));
        if (taskDto.equals(new TaskDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/project")
    public ResponseEntity<?> getProjectTasks(@RequestBody @Valid WithProjectPayload payload,
                                             Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.taskService
                .getProjectTasks(payload.getEmail(), payload.getProjectId())
                .stream().map(TaskDto::new).collect(Collectors.toList()), pageable, size));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTasks(@RequestBody @Valid AdminAccessPayload payload,
                                      Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.taskService
                .getTasks(payload.getAdminEmail(), payload.getToken())
                .stream().map(TaskDto::new).collect(Collectors.toList()), pageable, size));
    }
}
