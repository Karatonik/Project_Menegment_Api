package pl.project.ProjectManagement.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.model.request.UpdateRoleRequest;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 7200)
@Tag(name="person", description = "auth and person API")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService personService) {
        this.service = personService;
    }

    @PostMapping("/log")
    public ResponseEntity<?> authenticate(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return SmartResponseEntity.fromJWTResponse(service.authenticate(emailAndPassword));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> setPerson(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return SmartResponseEntity.fromBoolean(service.setPerson(emailAndPassword));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> getAdminToken(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return SmartResponseEntity.fromString(service.getAdminToken(emailAndPassword));
    }

    @PutMapping("/pass/{token}")
    public ResponseEntity<?> updatePersonPassword(@PathVariable String token, @RequestBody String newPassword) {
        return SmartResponseEntity.fromBoolean(service.updatePersonPassword(token, newPassword));
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<?> deletePerson(@PathVariable String token, @RequestBody String password) {
        return SmartResponseEntity.fromBoolean(service.deletePerson(token, password));
    }

    @PutMapping("/email/{token}")
    public ResponseEntity<?> updateEmail(@PathVariable String token, @RequestBody String newEmail) {
        return SmartResponseEntity.fromBoolean(service.updateEmail(token, newEmail));
    }

    @PutMapping("/role")
    ResponseEntity<?> updateRole(@RequestBody UpdateRoleRequest request) {
        return SmartResponseEntity.fromBoolean(service.updateRole(request.getAdminEmail(), request.getAdminToken(),
                request.getEmail(), request.getRole()));
    }

    @GetMapping("/all")
    ResponseEntity<List<Person>> getAllPerson(@RequestBody String adminEmail, @RequestBody String adminToken) {
        return ResponseEntity.ok(service.getAllPerson(adminEmail, adminToken));
    }


}
