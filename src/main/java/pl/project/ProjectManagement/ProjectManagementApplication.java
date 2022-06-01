package pl.project.ProjectManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.repository.PersonRepository;

import java.util.Optional;

@SpringBootApplication
public class ProjectManagementApplication {

    @Value("${admin.password}")
    String password;
    @Autowired
    private PersonRepository personRepository;
    @Value("${admin.email}")
    private String email;

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initAdmin() {
        Optional<Person> optionalAdmin = this.personRepository.findById(this.email);
        if (optionalAdmin.isEmpty()) {
            Person person = new Person(this.email, this.password);
            person.setRole(Role.ADMIN);
            this.personRepository.save(person);
            System.out.println("Create new Admin");
        } else {
            System.out.println("Admin is present");
        }

    }

}
