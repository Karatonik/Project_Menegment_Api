package pl.project.ProjectManagement.unitTests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.StudentController;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.enums.StudyType;
import pl.project.ProjectManagement.model.request.UpdateStudyTypePayload;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static pl.project.ProjectManagement.model.enums.StudyType.STATIONARY;

@ExtendWith(MockitoExtension.class)
public class StudentUnitTests {

    private final Person person = new Person("test@test.pl", "password123");
    private final Student student = new Student("test@test.pl", "Jan",
            "Kowalski", "11111", STATIONARY, Collections.emptySet(), new ArrayList<>(), person);

    private final StudentDto dto = new StudentDto(student);

    @Mock
    StudentService studentService;

    @Mock
    InfoService infoService;
    @Mock
    ModelWrapper modelWrapper;
    @InjectMocks
    StudentController studentController;

    @Test
    public void setStudent_OK() {
        when(studentService.setStudent(any(Student.class))).thenReturn(this.student);
        when(this.modelWrapper.getStudentFromDto(any(StudentDto.class))).thenReturn(this.student);

        ResponseEntity<?> response = studentController.setStudent(this.dto);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void getStudent_OK() {
        when(this.studentService.getStudent(anyString())).thenReturn(this.student);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ResponseEntity<?> response = studentController.getStudent("test@tes.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void updateStudentType_OK() {
        UpdateStudyTypePayload updateStudyTypePayload = new UpdateStudyTypePayload(StudyType.EXTRAMURAL);
        when(studentService.updateStudentType(anyString(), any())).thenReturn(true);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ResponseEntity<?> response = studentController.updateStudentType("test@test.pl"
                , updateStudyTypePayload);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateStudentType_BAD_REQUEST() {
        UpdateStudyTypePayload updateStudyTypePayload = new UpdateStudyTypePayload(StudyType.EXTRAMURAL);
        when(studentService.updateStudentType(anyString(), any())).thenReturn(false);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ResponseEntity<?> response = studentController.updateStudentType("test@test.pl"
                , updateStudyTypePayload);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }

    @Test
    public void joinToProject_OK() {
        when(this.studentService.joinToProject(anyString(), any())).thenReturn(true);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        long projectId = 1L;

        ResponseEntity<?> response = studentController.joinToProject("test@test.pl", projectId);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void joinToProject_BAD_REQEST() {
        when(this.studentService.joinToProject(anyString(), any())).thenReturn(false);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        long projectId = 1L;

        ResponseEntity<?> response = studentController.joinToProject("test@test.pl", projectId);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }

}
