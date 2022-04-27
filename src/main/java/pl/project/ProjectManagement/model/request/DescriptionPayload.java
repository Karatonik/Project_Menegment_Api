package pl.project.ProjectManagement.model.request;

import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DescriptionPayload {

    private String email;
    private Long projectId;
    private String description;


}
