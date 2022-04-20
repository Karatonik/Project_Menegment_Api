package pl.project.ProjectManagement.model.dto;

import lombok.*;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProjectDto {

    private Long projectId;

    private String name;

    private String description;

    private LocalDateTime dataAndTimeOfCreation;

    private AccessType access;

    private StatusType status;

    private LocalDateTime dataAndTimeOfUpdate;

    private LocalDate dateOfDelivery;

    private String projectOwnerEmail;

    private List<Long> tasksIds;

    private List<String> studentsEmails;

    public ProjectDto(Project project) {
        this.projectId = project.getProjectId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.dataAndTimeOfCreation = project.getDataAndTimeOfCreation();
        this.access = project.getAccess();
        this.status = project.getStatus();
        this.dataAndTimeOfUpdate = project.getDataAndTimeOfUpdate();
        this.dateOfDelivery = project.getDateOfDelivery();
        this.projectOwnerEmail = project.getProjectOwner().getEmail();
        this.tasksIds = project.getTasks().stream()
                .map(Task::getTaskId).collect(Collectors.toList());
        this.studentsEmails = project.getStudents().stream()
                .map(Student::getEmail).collect(Collectors.toList());
    }
}
