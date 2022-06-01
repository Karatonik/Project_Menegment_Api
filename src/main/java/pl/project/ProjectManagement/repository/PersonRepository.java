package pl.project.ProjectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, String> {

    Optional<Person> findByRole(Role role);

    Optional<Person> findByToken(String token);

    Optional<Person> findByEmailAndToken(String email, String token);

    Optional<Person> findByEmailAndPassword(String email, String password);
}
