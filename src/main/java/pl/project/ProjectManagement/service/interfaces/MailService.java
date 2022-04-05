package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.request.MailContent;

public interface MailService {

    Boolean sendMail(MailContent mailContent);
}
