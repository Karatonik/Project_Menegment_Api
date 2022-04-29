package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.TaskPayload;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResultAccessPayload extends TaskPayload {

    private String projectOwnerEmail;

    public ResultAccessPayload(long taskId, String projectOwnerEmail) {
        super(taskId);
        this.projectOwnerEmail = projectOwnerEmail;
    }
}
