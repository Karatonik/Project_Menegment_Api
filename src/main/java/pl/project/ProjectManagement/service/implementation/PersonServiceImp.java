package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.builder.PersonBuilder;
import pl.project.ProjectManagement.jwt.JwtUtils;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.AccessDataPayload;
import pl.project.ProjectManagement.model.response.JwtResponse;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImp implements PersonService {

    private final PersonRepository personRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public PersonServiceImp(PersonRepository personRepository
            , AuthenticationManager authenticationManager,
                            PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.personRepository = personRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean setPerson(AccessDataPayload emailAndPassword) {
        if (this.personRepository.existsById(emailAndPassword.getEmail())) {
            return false;
        }
        Person person = new Person(emailAndPassword.getEmail(),
                this.encoder.encode(emailAndPassword.getPassword()));
        this.personRepository.save(person);

        return true;
    }

    @Override
    public Optional<JwtResponse> authenticate(AccessDataPayload emailAndPassword) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(emailAndPassword.getEmail(),
                            emailAndPassword.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = this.jwtUtils.generateJwtToken(authentication);

            PersonBuilder personBuilder = (PersonBuilder) authentication.getPrincipal();

            return Optional.of( new JwtResponse(jwt, personBuilder.getEmail(), personBuilder.getRole()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public String getToken(String email) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            return person.getToken();
        }
        return "";
    }

    @Override
    public boolean updatePersonPassword(String token, String newPassword) {
        Optional<Person> optionalPerson = this.personRepository.findByToken(token);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setPassword(this.encoder.encode(newPassword));
            personRepository.save(person);

            return true;
        }
        return false;
    }

    @Override
    public boolean deletePerson(String token, String password) {
        Optional<Person> optionalPerson = this.personRepository.findByToken(token);
        if (optionalPerson.isPresent()) {
            this.personRepository.delete(optionalPerson.get());
            return true;
        }
        return false;
    }


    @Override
    public boolean updateRole(String adminEmail, String adminToken, String email, Role role) {
        Optional<Person> optionalAdmin = this.personRepository.findByEmailAndToken(adminEmail, adminToken);
        if (optionalAdmin.isPresent()) {
            Optional<Person> optionalPerson = this.personRepository.findById(email);
            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                person.setRole(role);
                this.personRepository.save(person);
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<Person> getAllPerson(String adminEmail, String adminToken, Pageable pageable) {
        Optional<Person> optionalAdmin = this.personRepository.findByEmailAndToken(adminEmail, adminToken);
        if (optionalAdmin.isPresent()) {
            return personRepository.findAll(pageable);
        }

        System.out.println("you are no admin");
        return Page.empty();
    }

    @Override
    public Optional<String> getAdminToken(AccessDataPayload emailAndPassword) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailAndPassword.getEmail(),
                        emailAndPassword.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        PersonBuilder personBuilder = (PersonBuilder) authentication.getPrincipal();

        if (personBuilder.getRole().equals(Role.ADMIN)) {
            Person person = this.personRepository.getById(personBuilder.getEmail());
            person.setToken();
            this.personRepository.save(person);
            return Optional.of(person.getToken());
        }
        return Optional.empty();
    }
}
