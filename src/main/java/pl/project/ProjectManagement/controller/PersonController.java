package pl.project.ProjectManagement.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.MailRole;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.model.request.MailContent;
import pl.project.ProjectManagement.model.request.UpdateRoleRequest;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.MailService;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 7200)
@Tag(name="person", description = "auth and person API")
public class PersonController {

    private final PersonService personService;
    private final MailService mailService;

    @Autowired
    public PersonController(PersonService service, MailService mailService) {
        this.personService = service;
        this.mailService = mailService;
    }

    @PostMapping("/log")
    public ResponseEntity<?> authenticate(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return SmartResponseEntity.fromJWTResponse(this.personService.authenticate(emailAndPassword));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> setPerson(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return SmartResponseEntity.fromBoolean(this.personService.setPerson(emailAndPassword));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> getAdminToken(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return SmartResponseEntity.fromString(this.personService.getAdminToken(emailAndPassword));
    }

    @PutMapping("/pass/{token}")
    public ResponseEntity<?> updatePersonPassword(@PathVariable String token, @RequestBody String newPassword) {
        return SmartResponseEntity.fromBoolean(this.personService.updatePersonPassword(token, newPassword));
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<?> deletePerson(@PathVariable String token, @RequestBody String password) {
        return SmartResponseEntity.fromBoolean(this.personService.deletePerson(token, password));
    }

    @PutMapping("/email/{token}")
    public ResponseEntity<?> updateEmail(@PathVariable String token, @RequestBody String newEmail) {
        return SmartResponseEntity.fromBoolean(this.personService.updateEmail(token, newEmail));
    }

    @PutMapping("/role")
    ResponseEntity<?> updateRole(@RequestBody UpdateRoleRequest request) {
        return SmartResponseEntity.fromBoolean(this.personService.updateRole(request.getAdminEmail(), request.getAdminToken(),
                request.getEmail(), request.getRole()));
    }

    @GetMapping("/all")
    ResponseEntity<List<Person>> getAllPerson(@RequestBody String adminEmail, @RequestBody String adminToken) {
        return ResponseEntity.ok(this.personService.getAllPerson(adminEmail, adminToken));
    }

    @PostMapping("/mail/{to}/{mailRole}")
    ResponseEntity<?> sendToken(@PathVariable String to, @PathVariable MailRole  mailRole){
        System.out.println(new MailContent(to, mailRole));
        return SmartResponseEntity.fromBoolean(this.mailService.sendMail(new MailContent(to, mailRole)));
    }
}
