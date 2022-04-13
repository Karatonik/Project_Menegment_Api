package pl.project.ProjectManagement.model.dto;

import lombok.*;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.StudyType;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentDto {
    private String email;

    private String name;

    private String surname;

    private String index_number;

    private StudyType StudyType;


    private Set<Long> projectsIds;

    private String personEmail;


    public StudentDto(Student student) {
        this.email = student.getEmail();
        this.name = student.getName();
        this.surname = student.getSurname();
        this.index_number = student.getIndex_number();
        StudyType = student.getStudyType();
        this.projectsIds = student.getProjects().stream()
                .map(Project::getProjectId).collect(Collectors.toSet());
        this.personEmail = student.getPerson().getEmail();
    }
}
