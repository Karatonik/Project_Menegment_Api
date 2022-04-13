package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import javax.validation.constraints.NotBlank;
import java.util.List;
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
    public ResponseEntity<TaskDto> setTask(@RequestBody @NotBlank TaskDto taskDto) {

        return ResponseEntity.ok(new TaskDto(this.taskService.setTask(modelWrapper.getTaskFromDto(taskDto))));
    }

    @GetMapping
    public ResponseEntity<TaskDto> getTask(@RequestBody @NotBlank Long taskId) {

        return ResponseEntity.ok(new TaskDto(this.taskService.getTask(taskId)));
    }

    @GetMapping("/project/{email}")
    public ResponseEntity<List<TaskDto>> getProjectTasks(@PathVariable @NotBlank String email,
                                                         @RequestBody @NotBlank Long projectId) {

        return  ResponseEntity.ok(this.taskService
                .getProjectTasks(email, projectId).stream().map(TaskDto::new).collect(Collectors.toList()));
    }

    @GetMapping("{adminEmail}")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable @NotBlank String  adminEmail,
                                               @RequestBody @NotBlank String token) {

        return ResponseEntity.ok(this.taskService
                .getTasks(adminEmail, token).stream().map(TaskDto::new).collect(Collectors.toList()));
    }
}
