package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectNamePayload extends ProjectPayload {

    private String name;

    public ProjectNamePayload(Long projectId, String name) {
        super(projectId);
        this.name = name;
    }
}
