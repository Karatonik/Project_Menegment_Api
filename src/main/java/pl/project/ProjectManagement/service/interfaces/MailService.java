package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.request.MailPayload;

public interface MailService {

    Boolean sendMail(MailPayload mailContent);
}
