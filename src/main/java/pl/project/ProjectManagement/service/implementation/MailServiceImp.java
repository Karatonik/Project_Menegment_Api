package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.request.MailContent;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.service.interfaces.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class MailServiceImp implements MailService {

    JavaMailSender javaMailSender;
    PersonRepository personRepository;

    @Value("${my.frontURL}")
    private String url;

    @Autowired
    public MailServiceImp( PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Boolean sendMail(MailContent mailContent) {//todo
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailContent.getTo());
            mimeMessageHelper.setSubject(mailContent.getSubject());

            switch (mailContent.getMailRole()){

                case None -> mimeMessageHelper
                        .setText(mailContent.getText(),
                        mailContent.isHtmlContent());
                case UpdateRole -> mimeMessageHelper
                        .setText(url+"/updaterole"+getPersonToken(mailContent.getTo())
                        , mailContent.isHtmlContent());
                case UpdateEmail -> mimeMessageHelper
                        .setText(url+"/updateemail"+getPersonToken(mailContent.getTo())
                        , mailContent.isHtmlContent());
                case DeletePerson -> mimeMessageHelper
                        .setText(url+"/deleteperson"+getPersonToken(mailContent.getTo())
                        , mailContent.isHtmlContent());
                case UpdatePassword -> mimeMessageHelper
                        .setText(url+"/updatepassword"+getPersonToken(mailContent.getTo())
                        , mailContent.isHtmlContent());
            }
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
        return false;
    }

    private String getPersonToken(String email){
        Optional<Person> optionalPerson = personRepository.findById(email);
        if(optionalPerson.isPresent()){
          Person person = optionalPerson.get();
          return person.getToken();
        }
        return "";
    }
}
