package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.project.ProjectManagement.model.TaskResult;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/res")
@CrossOrigin(origins = "*", maxAge = 7200)
public class TaskResultController {

    private final TaskResultService taskResultService;
    private final ModelWrapper modelWrapper;

    private final InfoService infoService;

    @Autowired
    public TaskResultController(TaskResultService taskResultService, ModelWrapper modelWrapper
            , InfoService infoService) {
        this.taskResultService = taskResultService;
        this.modelWrapper = modelWrapper;
        this.infoService = infoService;
    }

    @PostMapping
    public ResponseEntity<?> setTaskResult(@RequestPart("data") @Valid TaskResultDto taskResultDto, @RequestPart("file") MultipartFile file) {
        System.out.println(taskResultDto);
        System.out.println(file);
        return ResponseEntity.ok(new TaskResultDto(this.taskResultService.setTaskResult(this.modelWrapper.
                getTaskResultFromTaskResultDto(taskResultDto),file)));
    }

    @GetMapping("/{taskResultId}")
    public ResponseEntity<?> getTaskResult(@RequestHeader("Authorization") String authorization
            , @PathVariable long taskResultId) {
        System.out.println("taskid: " + taskResultId);
        return ResponseEntity.ok(new TaskResultDto(this.
                taskResultService.getTaskResult(taskResultId, this.infoService.getEmailFromJwt(authorization))));
    }
    @RequestMapping(value = "/file/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@RequestHeader("Authorization") String authorization
            , @PathVariable long taskId) throws IOException {
        System.out.println("Task id: " + taskId);
        return new FileSystemResource(taskResultService.getTaskResultFile(taskId, this.infoService.getEmailFromJwt(authorization)));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getTaskResultsByTask(@RequestHeader("Authorization") String authorization
            , @PathVariable long taskId, Pageable pageable) {
        Page<TaskResult> resultPage = taskResultService.
                getTaskResultsByTask(taskId, this.infoService.getEmailFromJwt(authorization), pageable);

        System.out.println(resultPage);
        return ResponseEntity.ok(new PageImpl<>(resultPage.stream()
                .map(TaskResultDto::new).toList(), pageable, resultPage.getTotalElements()));

    }
}
