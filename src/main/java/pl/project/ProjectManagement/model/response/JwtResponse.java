package pl.project.ProjectManagement.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.project.ProjectManagement.model.enums.Role;

@Getter
@Setter
@ToString
public class JwtResponse {
    private String jwToken;
    private final String type = "Bearer";
    private String email;
    private Role role;

    public JwtResponse() {
        this.jwToken="";
    }

    public JwtResponse(String jwToken, String email, Role role ) {
        this.jwToken = jwToken;
        this.email = email;
        this.role = role;
    }
}
