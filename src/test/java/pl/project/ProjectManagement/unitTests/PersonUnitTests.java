package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.PersonController;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonUnitTests {

    private final EmailAndPassword ep = new EmailAndPassword("test@test.com", "password123");
    @Mock
    PersonService personService;
    @InjectMocks
    PersonController personController;

    @Test
    public void setPerson_shouldContainStatus_OK() {
        when(personService.setPerson(any(EmailAndPassword.class))).thenReturn(true);

        ResponseEntity<?> response = personController.setPerson(ep);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setPerson_shouldContainStatus_BAD_REQUEST() {
        when(personService.setPerson(any(EmailAndPassword.class))).thenReturn(false);

        ResponseEntity<?> response = personController.setPerson(ep);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


}
