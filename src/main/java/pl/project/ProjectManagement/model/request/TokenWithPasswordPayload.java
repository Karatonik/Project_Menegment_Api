package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.TokenPayload;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TokenWithPasswordPayload extends TokenPayload {

    private String password;

    public TokenWithPasswordPayload(String token, String password) {
        super(token);
        this.password = password;
    }
}
