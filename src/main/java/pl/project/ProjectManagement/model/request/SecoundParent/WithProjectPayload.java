package pl.project.ProjectManagement.model.request.SecoundParent;

import lombok.*;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WithProjectPayload {

    private Long projectId;
}
