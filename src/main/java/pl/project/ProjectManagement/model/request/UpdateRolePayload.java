package pl.project.ProjectManagement.model.request;

import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;
import pl.project.ProjectManagement.model.enums.Role;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateRolePayload {

    private String adminEmail;
    private String adminToken;
    private String email;
    private Role role;
}
