package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.request.SecoundParent.WithProjectPayload;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectAccessPayload extends WithProjectPayload {

    private AccessType access;

    public ProjectAccessPayload(Long projectId, AccessType access) {
        super(projectId);
        this.access = access;
    }
}
