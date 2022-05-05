package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.request.MailPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.MailService;

import javax.validation.Valid;


@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*", maxAge = 7200)
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<?> sendMail(@RequestBody @Valid MailPayload mailContent) {
        return SmartResponseEntity.fromBoolean(mailService.sendMail(mailContent));
    }
}
