package pl.project.ProjectManagement.model.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectDeletePayload {

    private String email;

    private Long projectId;
}
