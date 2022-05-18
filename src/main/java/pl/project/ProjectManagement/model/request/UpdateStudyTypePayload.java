package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.StudyType;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateStudyTypePayload{

    private StudyType studyType;

}
