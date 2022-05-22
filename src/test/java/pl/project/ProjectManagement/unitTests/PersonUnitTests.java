package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.PersonController;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.request.AccessDataPayload;
import pl.project.ProjectManagement.model.request.TokenWithEmailPayload;
import pl.project.ProjectManagement.model.request.TokenWithPasswordPayload;
import pl.project.ProjectManagement.model.request.UpdateRolePayload;
import pl.project.ProjectManagement.model.response.JwtResponse;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonUnitTests {

    @Mock
    PersonService personService;
    @Mock
    InfoService infoService;

    private final AccessDataPayload accessData = new AccessDataPayload("test@test.com", "password123");

    private final TokenWithPasswordPayload tokenWithPasswordPayload = new TokenWithPasswordPayload("token",
            "test123");
    private final TokenWithEmailPayload tokenWithEmailPayload = new TokenWithEmailPayload("token",
            "test@test.com");
    @InjectMocks
    PersonController personController;

    @Test
    public void setPerson_shouldContainStatus_OK() {
        when(this.personService.setPerson(any(AccessDataPayload.class))).thenReturn(true );

        ResponseEntity<?> response = this.personController.setPerson(this.accessData);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setPerson_shouldContainStatus_BAD_REQUEST() {
        when(this.personService.setPerson(any(AccessDataPayload.class))).thenReturn(false);

        ResponseEntity<?> response = this.personController.setPerson(this.accessData);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
    @Test
    public void authenticate_shouldContainStatus_OK() {
        JwtResponse jwtResponse = new JwtResponse("Jwt","test@test.pl", Role.USER);
        when(this.personService.authenticate(any(AccessDataPayload.class))).thenReturn(Optional.of(jwtResponse));

        ResponseEntity<?> response = this.personController.authenticate(this.accessData);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void authenticate_shouldContainStatus_BAD_REQUEST() {
        when(this.personService.authenticate(any(AccessDataPayload.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = this.personController.authenticate(this.accessData);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAdminToken_shouldContainStatus_OK() {
        when(this.personService.getAdminToken(any(AccessDataPayload.class))).thenReturn(Optional.of("AdminToken"));

        ResponseEntity<?> response = this.personController.getAdminToken(this.accessData);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getAdminToken_shouldContainStatus_BAD_REQUEST() {
        when(this.personService.getAdminToken(any(AccessDataPayload.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = this.personController.getAdminToken(this.accessData);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }


    @Test
    void updatePersonPassword_shouldContainStatus_OK() {
        when(this.personService.updatePersonPassword(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = this.personController.updatePersonPassword(this.tokenWithPasswordPayload);

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }

    @Test
    void updatePersonPassword_shouldContainStatus_BAD_REQUEST() {
        when(this.personService.updatePersonPassword(anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = this.personController.updatePersonPassword(this.tokenWithPasswordPayload);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void deletePerson_shouldContainStatus_OK() {
        when(this.personService.deletePerson(anyString(),anyString())).thenReturn(true);

        ResponseEntity<?> response = this.personController.deletePerson(this.tokenWithPasswordPayload);

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }

    @Test
    void deletePerson_shouldContainStatus_BAD_REQUEST() {
        when(this.personService.deletePerson(anyString(),anyString())).thenReturn(false);

        ResponseEntity<?> response = this.personController.deletePerson(this.tokenWithPasswordPayload);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @Test
    void updateEmail_shouldContainStatus_OK() {
        when(this.personService.updateEmail(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = this.personController.updateEmail(this.tokenWithEmailPayload);

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }

    @Test
    void updateEmail_shouldContainStatus_BAD_REQUEST() {
        when(this.personService.updateEmail(anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = this.personController.updateEmail(this.tokenWithEmailPayload);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void updateRole_shouldContainStatus_OK() {
        UpdateRolePayload updateRolePayload = new UpdateRolePayload("test@test.pl","adminToken",Role.ADMIN);
        when(this.personService.updateRole(anyString(), anyString(),anyString(), any(Role.class))).thenReturn(true);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("adminEmail");

        ResponseEntity<?> response = personController.updateRole("adminEmail",updateRolePayload);

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }
    @Test
    void updateRole_shouldContainStatus_BAD_REQUEST() {
        UpdateRolePayload updateRolePayload = new UpdateRolePayload("test@test.pl","adminToken",Role.ADMIN);
        when(personService.updateRole(anyString(), anyString(),anyString(), any(Role.class))).thenReturn(false);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("adminEmail");

        ResponseEntity<?> response = personController.updateRole("adminEmail",updateRolePayload);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }



}
