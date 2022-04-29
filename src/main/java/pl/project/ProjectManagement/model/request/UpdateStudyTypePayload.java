package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.StudyType;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateStudyTypePayload extends EmailPayload {

    private StudyType studyType;

    public UpdateStudyTypePayload(@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "incorrect email") String email, StudyType studyType) {
        super(email);
        this.studyType = studyType;
    }
}
