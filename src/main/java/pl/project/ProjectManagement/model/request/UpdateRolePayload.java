package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateRolePayload extends EmailPayload {

    private String adminEmail;
    private String token;
    private Role role;

    public UpdateRolePayload(@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "incorrect email") String email, String adminEmail, String adminToken, Role role) {
        super(email);
        this.adminEmail = adminEmail;
        this.token = adminToken;
        this.role = role;
    }
}
