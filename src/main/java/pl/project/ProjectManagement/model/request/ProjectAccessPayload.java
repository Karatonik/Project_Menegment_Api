package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectAccessPayload extends ProjectPayload {

    private AccessType access;

    public ProjectAccessPayload(Long projectId, AccessType access) {
        super(projectId);
        this.access = access;
    }
}
