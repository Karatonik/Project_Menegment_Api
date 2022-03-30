package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import javax.validation.Valid;

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

}
