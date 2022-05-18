package pl.project.ProjectManagement.unitTests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.PersonController;
import pl.project.ProjectManagement.controller.StudentController;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.PersonService;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import javax.validation.constraints.NotBlank;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static pl.project.ProjectManagement.model.enums.StudyType.STATIONARY;
import pl.project.ProjectManagement.model.dto.StudentDto;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentUnitTests {

    private final Student ep = new Student("aaa@aa.pl", "Adam", "Wala", "113251", STATIONARY, any(), "aaa@tes1t.pl");

    @Mock
    StudentService studentService;
    @InjectMocks
    StudentController studentController;

    @Test
    public void setStudent_shouldContainStatus_OK() {
        when(studentService.setStudent(any(Student.class))).thenReturn(true);

        ResponseEntity<?> response = studentController.setStudent(ep);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }
    @Test
    public void setStudent_shouldContainStatus_BAD_REQUEST() {
        when(studentService.setStudent(any(Student.class))).thenReturn(false);

        ResponseEntity<?> response = studentController.setStudent(ep);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getStudent_shouldContainStatus_OK() {
        when(studentService.getStudent(anyString())).thenReturn(true);

        ResponseEntity<?> response = studentController.getStudent("test@tes.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void getStudent_shouldContainStatus_BAD_REQUEST() {
        when(studentService.getStudent(anyString())).thenReturn(false);

        ResponseEntity<?> response = studentController.getStudent("asv@@.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }

    @Test
    public void updateStudentType_OK() {
        when(studentService.updateStudentType(anyString(), any())).thenReturn(true);

        ResponseEntity<?> response = studentController.updateStudentType("test@test.pl", STATIONARY);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateStudentType_BAD_REQUEST(){
        when(studentService.updateStudentType(anyString(), any())).thenReturn(false);

        ResponseEntity<?> response = studentController.updateStudentType("t@@.pl", null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }

    @Test
    public void joinToProject_OK() {
        when(studentService.joinToProject(anyString(), any())).thenReturn(true);

        ResponseEntity<?> response = studentController.joinToProject("email@test.pl", 1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void joinToProject_BAD_REQEST() {
        when(studentService.joinToProject(anyString(), any())).thenReturn(false);

        ResponseEntity<?> response = studentController.joinToProject("E@@.pl", -25L);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }



}
