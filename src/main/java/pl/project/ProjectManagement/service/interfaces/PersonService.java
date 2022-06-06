package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.AccessDataPayload;
import pl.project.ProjectManagement.model.response.JwtResponse;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    boolean setPerson(AccessDataPayload emailAndPassword);

    Optional<JwtResponse> authenticate(AccessDataPayload emailAndPassword);

    String getToken(String email);

    boolean updatePersonPassword(String token, String newPassword);

    boolean deletePerson(String token, String password);
    boolean updateRole(String adminEmail, String adminToken, String email, Role role);

    Page<Person> getAllPerson(String adminEmail, String adminToken, Pageable pageable);

    Optional<String> getAdminToken(AccessDataPayload emailAndPassword);
}
