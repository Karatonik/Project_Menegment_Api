package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import javax.validation.Valid;

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
        return ResponseEntity.ok(new TaskDto(this.taskService.setTask(modelWrapper.getTaskFromDto(taskDto))));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable long taskId) {
        return ResponseEntity.ok(new TaskDto(this.taskService.getTask(taskId)));
    }

    //todo
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getProjectTasks(@RequestHeader("Authorization") String authorization
            , @PathVariable long projectId, Pageable pageable) {
        Page<Task> taskPage = this.taskService.getProjectTasks(this.infoService
                .getEmailFromJwt(authorization), projectId, pageable);

        return ResponseEntity.ok(new PageImpl<>(taskPage.stream().map(TaskDto::new).toList()
                , pageable, taskPage.getTotalElements()));
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<?> getTasks(@RequestHeader("Authorization") String authorization
            , @PathVariable String token, Pageable pageable) {

        Page<Task> taskPage = this.taskService.getTasks(this.infoService
                .getEmailFromJwt(authorization), token, pageable);

        return ResponseEntity.ok(new PageImpl<>(taskPage.stream()
                .map(TaskDto::new).toList(), pageable, taskPage.getTotalElements()));
    }
}
