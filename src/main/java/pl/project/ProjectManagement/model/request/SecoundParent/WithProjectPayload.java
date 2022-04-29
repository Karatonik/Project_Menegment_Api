package pl.project.ProjectManagement.model.request.SecoundParent;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WithProjectPayload extends EmailPayload {

    private Long projectId;

    public WithProjectPayload(@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "incorrect email") String email, Long projectId) {
        super(email);
        this.projectId = projectId;
    }
}
