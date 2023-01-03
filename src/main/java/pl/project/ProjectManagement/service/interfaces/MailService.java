package pl.project.ProjectManagement.service.interfaces;

public interface MailService {
    boolean sendMail(String email, String subject, String text);
}
