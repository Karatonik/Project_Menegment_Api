package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.request.Parent.TokenPayload;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenWithPasswordPayload extends TokenPayload {

    private String password;

    public TokenWithPasswordPayload(String token, String password) {
        super(token);
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +"token=" +getToken()+
                ", password='" + password + '\'' +
                '}';
    }
}
