package pl.project.ProjectManagement.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "project")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long projectId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 100)
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataAndTimeOfCreation;

    @Column(columnDefinition = "integer default 0") //access = Close
    private AccessType access;

    @Column(columnDefinition = "integer default 0") //status = Continues
    private StatusType status;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAndTimeOfUpdate;

    private LocalDate dateOfDelivery;

    @ManyToOne
    @JoinColumn(name = "email")
    private Person projectOwner;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<Student> students;

    public Project(String name, String description, LocalDateTime dataAndTimeOfCreation,
                   LocalDateTime dataAndTimeOfUpdate, LocalDate dateOfDelivery, List<Task> tasks,
                   List<Student> students, Person projectOwner) {
        this.name = name;
        this.description = description;
        this.dataAndTimeOfCreation = dataAndTimeOfCreation;
        this.dataAndTimeOfUpdate = dataAndTimeOfUpdate;
        this.dateOfDelivery = dateOfDelivery;
        this.tasks = tasks;
        this.students = students;
        this.projectOwner = projectOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return Objects.equals(getProjectId(), project.getProjectId()) &&
                Objects.equals(getName(), project.getName()) &&
                Objects.equals(getDescription(), project.getDescription()) &&
                Objects.equals(getDataAndTimeOfCreation(), project.getDataAndTimeOfCreation()) &&
                getAccess() == project.getAccess() && getStatus() == project.getStatus() &&
                Objects.equals(getDataAndTimeOfUpdate(), project.getDataAndTimeOfUpdate()) &&
                Objects.equals(getDateOfDelivery(), project.getDateOfDelivery()) &&
                Objects.equals(getProjectOwner(), project.getProjectOwner()) &&
                Objects.equals(getTasks(), project.getTasks()) &&
                Objects.equals(getStudents(), project.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getName(), getDescription(),
                getDataAndTimeOfCreation(), getAccess(), getStatus(),
                getDataAndTimeOfUpdate(), getDateOfDelivery(), getProjectOwner(),
                getTasks(), getStudents());
    }
}

