package pl.project.ProjectManagement.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.project.ProjectManagement.model.enums.Role;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String jwToken;
    private final String type = "Bearer";
    private String email;
    private Role role;


    public JwtResponse(String jwToken, String email,Role role ) {
        this.jwToken = jwToken;
        this.email = email;
        this.role = role;
    }
}
