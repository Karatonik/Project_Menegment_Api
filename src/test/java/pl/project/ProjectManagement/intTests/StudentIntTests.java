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
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.PersonService;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private final EmailAndPassword ep = new EmailAndPassword("test@test.com", "password123");
    @MockBean
    StudentService studentService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModelWrapper modelWrapper;



    @Test
    public void setStudent_shouldContainStatus_OK() throws Exception {
        when(studentService.setStudent(any(Student.class))).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(ep);

        mvc.perform(post(String.format("", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

    }
    @Test
    public void setStudent_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(studentService.setStudent(any(Student.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(ep);

        mvc.perform(post(String.format("", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));
    }

    @Test
    public void getStudent_shouldContainStatus_OK() throws Exception {
        when(studentService.getStudent(anyString())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString("test@test.pl");

        mvc.perform(post(String.format("%s/{email}", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

    }

    @Test
    public void getStudent_shouldContainStatus_BAD_REQUEST() throws Exception {
        when(studentService.getStudent(anyString())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString("aaa@@.pl");

        mvc.perform(post(String.format("%s/{email}", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));

    }

    @Test
    public void updateStudentType_OK() throws Exception {
        when(studentService.updateStudentType(anyString(), any())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString("test@test.pl", STATIONARY);

        mvc.perform(post(String.format("%s/{email}", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void updateStudentType_BAD_REQUEST() throws Exception {
        when(studentService.updateStudentType(anyString(), any())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString("aaa@@.pl", null);

        mvc.perform(post(String.format("%s/{email}", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));

    }

    @Test
    public void joinToProject_OK() throws Exception {
        when(studentService.joinToProject(anyString(), any())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString("test@test.pl", 1L);

        mvc.perform(post(String.format("%s/join/{email}", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

    }

    @Test
    public void joinToProject_BAD_REQEST()  throws Exception{
        when(studentService.joinToProject(anyString(), any())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString("aaa@@.pl", -25L);

        mvc.perform(post(String.format("%s/join/{email}", path)).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("BAD_REQUEST")));

    }




}
