package pl.project.ProjectManagement.model.request.Parent;

import lombok.*;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminAccessPayload extends TokenPayload{
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
    private String adminEmail;

    public AdminAccessPayload(String token, String adminEmail) {
        super(token);
        this.adminEmail = adminEmail;
    }
}
