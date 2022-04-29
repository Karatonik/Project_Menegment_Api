package pl.project.ProjectManagement.model.request.Parent;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminAccessPayload {

    private String adminEmail;

    private String token;
}
