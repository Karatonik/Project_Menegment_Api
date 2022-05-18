package pl.project.ProjectManagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccessDataPayload{

    private String email;

    @Size(min = 8)
    private String password;
}
