package pl.project.ProjectManagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccessDataPayload extends EmailPayload {

    @Size(min = 8)
    private String password;

    public AccessDataPayload(@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
            , message = "incorrect email") String email, String password) {
        super(email);
        this.password = password;
    }
}
