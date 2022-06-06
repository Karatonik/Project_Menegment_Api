package pl.project.ProjectManagement.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.dto.PersonDto;
import pl.project.ProjectManagement.model.request.*;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 7200)
@Tag(name = "person")
public class PersonController {

    private final PersonService personService;
    private final InfoService infoService;

    @Autowired
    public PersonController(PersonService service, InfoService infoService) {
        this.personService = service;
        this.infoService = infoService;
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
    public ResponseEntity<?> updatePersonPassword(@RequestBody TokenWithPasswordPayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService
                .updatePersonPassword(payload.getToken(), payload.getPassword()));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(@Valid @RequestBody TokenWithPasswordPayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService
                .deletePerson(payload.getToken(), payload.getPassword()));
    }
    @PutMapping("/role")
    public ResponseEntity<?> updateRole(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid UpdateRolePayload payload) {
        return SmartResponseEntity.fromBoolean(this.personService.
                updateRole(this.infoService.getEmailFromJwt(authorization), payload.getToken()
                        , payload.getEmail(), payload.getRole()));
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<?> getAllPerson(@RequestHeader("Authorization") String authorization
            ,@PathVariable String token, Pageable pageable) {

        Page<Person> personPage = this.personService.getAllPerson(this.infoService
                .getEmailFromJwt(authorization), token, pageable);

        return ResponseEntity.ok(new PageImpl<>(personPage.stream().map(PersonDto::new).toList(),pageable
                , personPage.getTotalElements()));
    }
    @GetMapping("/token")
    public ResponseEntity<?> sendToken(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(this.personService.getToken(this.infoService
                .getEmailFromJwt(authorization)));
    }
}
