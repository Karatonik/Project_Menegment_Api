package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DescriptionPayload extends ProjectPayload {

    private String description;

    public DescriptionPayload(Long projectId, String description) {
        super(projectId);
        this.description = description;
    }
}
