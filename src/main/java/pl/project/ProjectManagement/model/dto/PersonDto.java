package pl.project.ProjectManagement.model.dto;

import lombok.*;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.enums.Role;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonDto {

    private String email;

    private Role role;

    private List<Long> ownedProjects;

    public PersonDto(Person person) {
        this.email = person.getEmail();
        this.role = person.getRole();
        this.ownedProjects = person.getOwnedProjects().stream()
                .map(Project::getProjectId).collect(Collectors.toList());
    }
}
