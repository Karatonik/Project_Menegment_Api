package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.MailRole;
import pl.project.ProjectManagement.model.request.MailContent;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.service.interfaces.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Optional;

@Service
public class MailServiceImp implements MailService {

    private final Environment environment;

    JavaMailSender javaMailSender;
    PersonRepository personRepository;

    @Value("${my.frontURL}")
    private String url;

    @Autowired
    public MailServiceImp(PersonRepository personRepository, Environment environment) {
        this.personRepository = personRepository;
        this.environment = environment;
    }

    @Override
    public Boolean sendMail(MailContent mailContent) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailContent.getTo());
            if (mailContent.getMailRole().equals(MailRole.None)) {
                mimeMessageHelper.setSubject(mailContent.getSubject());
                mimeMessageHelper.setText(mailContent.getText(),
                        mailContent.isHtmlContent());
            } else {
                mimeMessageHelper.setSubject(setSubject(mailContent.getMailRole()));
                mimeMessageHelper.setText(setText(mailContent.getMailRole(),
                                getPersonToken(mailContent.getTo())),
                        mailContent.isHtmlContent());
            }
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
        return false;
    }

    private String getPersonToken(String email) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            return person.getToken();
        }
        return "";
    }

    private String setSubject(MailRole mailRole) {
        return Objects.requireNonNull(environment.getProperty("subject." + mailRole));
    }

    private String setText(MailRole mailRole, String token) {
        return String.format("%s/%s/%s", this.url, mailRole, token);
    }
}
