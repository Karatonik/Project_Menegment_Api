package pl.project.ProjectManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.repository.PersonRepository;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootApplication
public class ProjectManagementApplication {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PersonRepository personRepository;
    @Value("${admin.email}")
    private String email;

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initAdmin() {
       // Optional<Person> optionalAdmin = this.personRepository.findByRole(Role.ADMIN);
      //  if (optionalAdmin.isEmpty()) {
            String password = new Random().ints(10, 33, 122)
                    .mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());

            Person person = new Person(this.email,  this.encoder.encode(password));
            person.setRole(Role.ADMIN);
            this.personRepository.save(person);
            System.out.printf("Create new Admin:log:%s, pass %s%n",person.getEmail(),password);
        //} else {
          //  System.out.println("Admin is present");
        //}

    }

}
