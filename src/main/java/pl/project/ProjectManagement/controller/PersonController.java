package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 7200)
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService personService) {
        this.service = personService;
    }

    @PostMapping("/log")
    public ResponseEntity<?> authenticate(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return ResponseEntity.ok(service.authenticate(emailAndPassword));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> setPerson(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return ResponseEntity.ok(service.setPerson(emailAndPassword));
    }

    @PostMapping("/admin")
    public ResponseEntity<String> getAdminToken(@Valid @RequestBody EmailAndPassword emailAndPassword) {
        return ResponseEntity.ok(service.getAdminToken(emailAndPassword));
    }

    @PutMapping("/pass/{token}")
    public ResponseEntity<Boolean> updatePersonPassword(@PathVariable String token, @RequestBody String newPassword) {
        return ResponseEntity.ok(service.updatePersonPassword(token, newPassword));
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable String token, @RequestBody String password) {
        return ResponseEntity.ok(service.deletePerson(token, password));
    }

    @PutMapping("/mail")
    public ResponseEntity<Boolean> updateEmail(@RequestBody String token, @RequestBody String newEmail) {
        return ResponseEntity.ok(service.updateEmail(token, newEmail));
    }

    @PutMapping("/role")
    ResponseEntity<Boolean> updateRole(@RequestBody String adminEmail
            , @RequestBody String adminToken, @RequestBody String email, @RequestBody Role role) {
        return ResponseEntity.ok(service.updateRole(adminEmail, adminToken, email, role));
    }
    @GetMapping("/all")
    ResponseEntity<List<Person>> getAllPerson(@RequestBody String adminEmail,@RequestBody String adminToken){
        return ResponseEntity.ok(service.getAllPerson(adminEmail,adminToken));
    }


}
