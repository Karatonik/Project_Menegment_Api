package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.PersonController;
import pl.project.ProjectManagement.service.interfaces.PersonService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonUnitTests {
   /*
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

    @Test
      void getAdminToken() {
     }

    @Test
    void updatePersonPassword_shouldContainStatus_OK() {
        when(personService.updatePersonPassword(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = personController.updatePersonPassword("token", "test123");

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }

    @Test
    void updatePersonPassword_shouldContainStatus_BAD_REQUEST() {
        when(personService.updatePersonPassword(anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = personController.updatePersonPassword("token", "test123");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void deletePerson_shouldContainStatus_OK() {
        when(personService.deletePerson(anyString(),anyString())).thenReturn(true);

        ResponseEntity<?> response = personController.deletePerson(" token", " pass");

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }

    @Test
    void deletePerson_shouldContainStatus_BAD_REQUEST() {
        when(personService.deletePerson(anyString(),anyString())).thenReturn(false);

        ResponseEntity<?> response = personController.deletePerson(" token", " pass");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @Test
    void updateEmail_shouldContainStatus_OK() {
        when(personService.updateEmail(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = personController.updateEmail(" token", " new@email.pl");

        assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
    }

    @Test
    void updateEmail_shouldContainStatus_BAD_REQUEST() {
        when(personService.updateEmail(anyString(), anyString())).thenReturn(false);

        ResponseEntity<?> response = personController.updateEmail(" token", " new@email.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void updateRole() {
    }


//    @Test
//    void getAllPerson() {
//        List<Person> list =
//                List.of(new Person("email1@onet.pl","haslo123"),
//                        new Person("email2@onet.pl","haslo123"),
//                        new Person("email3@onet.pl","haslo123"),
//        PageRequest pageable = PageRequest.of(1, 5);
//        Page<Person> page = new PageImpl<>(list, pageable, 5);
//        when(personService.getAllPerson(pageable)).thenReturn(page);
//
//        Page<Person> pageWithProjects = personController.getAllPerson(pageable);
//
//        assertNotNull(pageWithProjects);
//        List<Person> persons = pageWithProjects.getContent();
//        assertNotNull(persons);
//        assertThat(persons, hasSize(3));
//        assertAll(() -> assertTrue(persons.contains(list.get(0))),
//                () -> assertTrue(persons.contains(list.get(1))),
//                () -> assertTrue(persons.contains(list.get(2))));
//

    // }

    */


}
