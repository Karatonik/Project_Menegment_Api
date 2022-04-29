package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.TokenPayload;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TokenWithEmailPayload extends TokenPayload {
    private String email;

    public TokenWithEmailPayload(String token, String email) {
        super(token);
        this.email = email;
    }
}
