package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.AccessDataPayload;
import pl.project.ProjectManagement.model.response.JwtResponse;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    boolean setPerson(AccessDataPayload emailAndPassword);

    Optional<JwtResponse> authenticate(AccessDataPayload emailAndPassword);

    boolean updatePersonPassword(String token, String newPassword);

    boolean deletePerson(String token, String password);

    boolean updateEmail(String token, String newEmail);

    boolean updateRole(String adminEmail, String adminToken, String email, Role role);

    List<Person> getAllPerson(String adminEmail, String adminToken);

    Optional<String> getAdminToken(AccessDataPayload emailAndPassword);
}
