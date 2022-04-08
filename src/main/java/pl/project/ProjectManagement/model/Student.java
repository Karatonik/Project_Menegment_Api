package pl.project.ProjectManagement.model;

import lombok.*;
import pl.project.ProjectManagement.model.enums.StudyType;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "student")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student {
    @Id
    @Column(length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(nullable = false, unique = true)
    private String index_number;

    @Column(columnDefinition = "integer default 0")
    private StudyType StudyType;

    @ManyToMany(mappedBy = "students")
    @ToString.Exclude
    private Set<Project> projects;
    @OneToOne
    private Person person;

    public Student(String name, String surname, String index_number,
                    Set<Project> projects, Person person) {
        this.name = name;
        this.surname = surname;
        this.index_number = index_number;
        this.projects = projects;
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(getEmail(), student.getEmail()) &&
                Objects.equals(getName(), student.getName()) &&
                Objects.equals(getSurname(), student.getSurname()) &&
                Objects.equals(getIndex_number(), student.getIndex_number()) &&
                getStudyType() == student.getStudyType() &&
                Objects.equals(getProjects(), student.getProjects()) &&
                Objects.equals(getPerson(), student.getPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getName(), getSurname(),
                getIndex_number(), getStudyType(), getProjects(), getPerson());
    }
}
