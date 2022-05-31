package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import javax.validation.Valid;
import java.util.List;
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
        return ResponseEntity.ok(new TaskDto(this.taskService.setTask(modelWrapper.getTaskFromDto(taskDto))));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable long taskId) {
        return ResponseEntity.ok(new TaskDto(this.taskService.getTask(taskId)));
    }
//todo
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getProjectTasks(@RequestHeader("Authorization") String authorization,
                                             @PathVariable long projectId,
                                             Pageable pageable) {
        System.out.println(pageable);
        List<TaskDto> tasks = this.taskService
                .getProjectTasks(this.infoService.getEmailFromJwt(authorization), projectId)
                .stream().map(TaskDto::new).toList();

        int start = (int) pageable.getOffset()*pageable.getPageSize() ;
        if(start>tasks.size()){
            start = tasks.size();
        }
        int end = Math.min((start + pageable.getPageSize()), pageable.getPageSize());
        if(end> tasks.size()){
            end = tasks.size();
        }

        return ResponseEntity.ok(new PageImpl<>(tasks.subList(start,end), pageable, tasks.size()));
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<?> getTasks(@RequestHeader("Authorization") String authorization,
                                      @PathVariable String token, Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.taskService
                .getTasks(this.infoService.getEmailFromJwt(authorization), token)
                .stream().map(TaskDto::new).collect(Collectors.toList()), pageable, size));
    }
}
