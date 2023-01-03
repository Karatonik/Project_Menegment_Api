package pl.project.ProjectManagement.model;

import lombok.*;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "task")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "task_id")
    private Long taskId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTimeAdded;

    @Column(columnDefinition = "integer default 0")
    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "task")
    @ToString.Exclude
    private List<TaskResult> taskResults;

    public Task(String name, Integer orderNumber, String description, LocalDateTime dateTimeAdded, Project project) {
        this.name = name;
        this.orderNumber = orderNumber;
        this.description = description;
        this.dateTimeAdded = dateTimeAdded;
        this.project = project;
        this.taskStatus = TaskStatus.Backlog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(getTaskId(), task.getTaskId()) && Objects.equals(getName(), task.getName()) &&
                Objects.equals(getOrderNumber(), task.getOrderNumber()) &&
                Objects.equals(getDescription(), task.getDescription()) &&
                Objects.equals(getDateTimeAdded(), task.getDateTimeAdded()) &&
                Objects.equals(getProject(), task.getProject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), getName(), getOrderNumber(), getDescription(),
                getDateTimeAdded(), getProject());
    }
}
