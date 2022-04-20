package pl.project.ProjectManagement.model.dto;

import lombok.*;
import pl.project.ProjectManagement.model.TaskResult;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TaskResultDto {

    private long resultId;

    private String studentEmail;

    private long taskId;

    private String fileName;

    private LocalDateTime dateOfUpload;

    public TaskResultDto(TaskResult taskResult) {
        this.resultId = taskResult.getResultId();
        this.studentEmail = taskResult.getStudent().getEmail();
        this.taskId = taskResult.getTask().getTaskId();
        this.fileName = taskResult.getFileName();
        this.dateOfUpload = taskResult.getDateOfUpload();

    }
}
