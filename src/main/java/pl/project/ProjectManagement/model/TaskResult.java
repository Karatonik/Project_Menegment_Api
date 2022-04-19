package pl.project.ProjectManagement.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "result")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskResult {

    @Id
    @GeneratedValue
    @Column(name = "result_id")
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "email")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String fileName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateOfUpload;

    public TaskResult(Student student, Task task,
                      String fileName, LocalDateTime dateOfUpload) {
        this.student = student;
        this.task = task;
        this.fileName = fileName;
        this.dateOfUpload = dateOfUpload;
    }
}
