package pl.project.ProjectManagement.model.request;

import lombok.*;
import pl.project.ProjectManagement.model.enums.AuthorType;
import pl.project.ProjectManagement.model.enums.MailRole;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailContent {
    private String to;

    private String subject;

    private String text;

    private boolean isHtmlContent;

    private MailRole mailRole;

    private AuthorType authorType;

    public MailContent(String to, MailRole mailRole) {
        this.to = to;
        this.mailRole = mailRole;
        this.isHtmlContent = true;

    }
}
