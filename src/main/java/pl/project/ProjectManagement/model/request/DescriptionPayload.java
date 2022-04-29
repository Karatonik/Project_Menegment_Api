package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.SecoundParent.WithProjectPayload;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DescriptionPayload extends WithProjectPayload {

    private String description;

    public DescriptionPayload(String email, Long projectId, String description) {
        super(email, projectId);
        this.description = description;
    }
}
