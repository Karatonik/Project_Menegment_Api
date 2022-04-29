package pl.project.ProjectManagement.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.MailRole;
import pl.project.ProjectManagement.model.request.*;
import pl.project.ProjectManagement.model.request.Parent.AdminAccessPayload;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.MailService;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 7200)
@Tag(name = "person")
public class PersonController {

    private final PersonService personService;
    private final MailService mailService;

    @Autowired
    public PersonController(PersonService service, MailService mailService) {
        this.personService = service;
        this.mailService = mailService;
    }

    @PostMapping("/log")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AccessDataPayload payload) {
        return SmartResponseEntity.fromOptional(this.personService.authenticate(payload));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> setPerson(@Valid @RequestBody AccessDataPayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService.setPerson(payload));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> getAdminToken(@Valid @RequestBody AccessDataPayload payload) {
        return SmartResponseEntity.fromOptional(this.personService.getAdminToken(payload));
    }

    @PutMapping("/pass")
    public ResponseEntity<?> updatePersonPassword(@Valid @RequestBody TokenWithPasswordPayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService
                .updatePersonPassword(payload.getToken(), payload.getPassword()));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(@Valid @RequestBody TokenWithPasswordPayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService.deletePerson(payload.getToken(),
                payload.getPassword()));
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@Valid @RequestBody TokenWithEmailPayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService.updateEmail(payload.getToken(),
                payload.getEmail()));
    }

    @PutMapping("/role")
    ResponseEntity<?> updateRole(@RequestBody UpdateRolePayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService.updateRole(payload.getAdminEmail(),
                payload.getToken(), payload.getEmail(), payload.getRole()));
    }

    @GetMapping("/all")
    ResponseEntity<List<Person>> getAllPerson(@RequestBody AdminAccessPayload payload) {
        return ResponseEntity.ok(this.personService.getAllPerson(payload.getAdminEmail(), payload.getToken()));
    }

    @PostMapping("/mail/{mailRole}")
    ResponseEntity<?> sendToken(@RequestBody EmailPayload payload, @PathVariable MailRole mailRole) {
        return SmartResponseEntity.fromBoolean(this.mailService
                .sendMail(new MailPayload(payload.getEmail(), mailRole)));
    }
}
