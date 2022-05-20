package pl.project.ProjectManagement.intTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.project.ProjectManagement.ProjectManagementApplication;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.enums.StudyType;
import pl.project.ProjectManagement.model.request.UpdateStudyTypePayload;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.PersonService;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.project.ProjectManagement.model.enums.StudyType.STATIONARY;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProjectManagementApplication.class})
@SpringBootTest
public class StudentIntTests {

    private final String path = "/student";
    @MockBean
    StudentService studentService;

    @MockBean
    InfoService infoService;
    private final Person person = new Person("test@test.pl", "password123");
    private final Student student = new Student("test@test.pl","Jan",
            "Kowalski","11111", STATIONARY, Collections.emptySet() ,new ArrayList<>(),person );
    private final StudentDto dto = new StudentDto(student);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModelWrapper modelWrapper;



    @Test
    public void setStudent_OK() throws Exception {
        when(this.studentService.setStudent(any(Student.class))).thenReturn(this.student);
        when(this.modelWrapper.getStudentFromDto(any(StudentDto.class))).thenReturn(this.student);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.dto);

        this.mvc.perform(post(String.format("%s", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.pl")));

    }



    @Test
    public void getStudent_OK() throws Exception {
        when(this.studentService.getStudent(anyString())).thenReturn(this.student);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        this.mvc.perform(get(String.format("%s", this.path))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Jan")));

    }

    @Test
    public void updateStudentType_OK() throws Exception {
        UpdateStudyTypePayload updateStudyTypePayload = new UpdateStudyTypePayload(StudyType.EXTRAMURAL);

        when(this.studentService.updateStudentType(anyString(), any())).thenReturn(true);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(updateStudyTypePayload);

        this.mvc.perform(put(String.format("%s/type", path)).content(requestJson)
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void updateStudentType_withoutHeader_BadRequest() throws Exception {
        UpdateStudyTypePayload updateStudyTypePayload = new UpdateStudyTypePayload(StudyType.EXTRAMURAL);

        when(this.studentService.updateStudentType(anyString(), any())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(updateStudyTypePayload);

        this.mvc.perform(put(String.format("%s/type", this.path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateStudentType_BAD_REQUEST() throws Exception {
        UpdateStudyTypePayload updateStudyTypePayload = new UpdateStudyTypePayload(StudyType.EXTRAMURAL);

        when(this.studentService.updateStudentType(anyString(), any())).thenReturn(false);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(updateStudyTypePayload);

        this.mvc.perform(put(String.format("%s/type", this.path)).content(requestJson)
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }

    @Test
    public void joinToProject_OK() throws Exception {
        when(this.studentService.joinToProject(anyString(), any())).thenReturn(true);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        long projectId = 1L;

        this.mvc.perform(put(String.format("%s/join/%d", this.path, projectId))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

    }

    @Test
    public void joinToProject_withoutHeader_BadRequest() throws Exception {
        when(this.studentService.joinToProject(anyString(), any())).thenReturn(true);
        long projectId = 1L;

        this.mvc.perform(put(String.format("%s/join/%d", this.path,projectId))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }
    @Test
    public void joinToProject_BAD_REQUEST() throws Exception {
        when(this.studentService.joinToProject(anyString(), any())).thenReturn(false);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        long projectId = 1L;

        this.mvc.perform(put(String.format("%s/join/%d", this.path, projectId))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }





}
