package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.service.interfaces.MailService;
@Service
public class MailServiceImp implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String apiEmail;

    @Override
    public boolean sendMail(String email, String subject, String text) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);
            mailMessage.setFrom(apiEmail);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
