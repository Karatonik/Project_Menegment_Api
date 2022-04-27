package pl.project.ProjectManagement.model.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectNamePayload {

    private String email;

    private Long projectId;

    private String name;
}
