package pl.project.ProjectManagement.model.dto;

import lombok.*;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TaskDto {

    private Long taskId;

    private String name;

    private Integer orderNumber;

    private String description;

    private LocalDateTime dateTimeAdded;


    private Long projectIds;

    public TaskDto(Task task) {
        this.taskId = task.getTaskId();
        this.name = task.getName();
        this.orderNumber = task.getOrderNumber();
        this.description = task.getDescription();
        this.dateTimeAdded = task.getDateTimeAdded();
        this.projectIds = task.getProject().getProjectId();
    }
}
