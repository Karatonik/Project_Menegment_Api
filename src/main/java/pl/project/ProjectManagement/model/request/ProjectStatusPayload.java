package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectStatusPayload extends ProjectPayload {

    private StatusType status;

    public ProjectStatusPayload(Long projectId, StatusType status) {
        super(projectId);
        this.status = status;
    }
}
