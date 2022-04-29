package pl.project.ProjectManagement.model.request.Parent;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminAccessPayload extends TokenPayload{

    private String adminEmail;

    public AdminAccessPayload(String token, String adminEmail) {
        super(token);
        this.adminEmail = adminEmail;
    }
}
