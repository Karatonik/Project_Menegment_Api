package pl.project.ProjectManagement.intTests;

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
import pl.project.ProjectManagement.model.*;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

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
public class TaskResultIntTests {

    private final String path = "/res";
    private final Person person = new Person("test@test.pl", "password123");
    private final Student student = new Student("test@test.pl", "Jan",
            "Kowalski", "11111", STATIONARY, Collections.emptySet(), new ArrayList<>(), this.person);
    private final Project project = new Project(1L, "Test", "Opis", LocalDateTime.now(),
            AccessType.OPEN, StatusType.CONTINUES, LocalDateTime.now(), LocalDate.now(), this.person, new ArrayList<>(), new ArrayList<>());
    private final Task task = new Task(1L, "Test", 1, "Opis", LocalDateTime.now(), this.project, new ArrayList<>());
    private final TaskResult taskResult = new TaskResult(1L, this.student, this.task, "file.pdf", LocalDateTime.now());
    private final TaskResultDto dto = new TaskResultDto(this.taskResult);
    @MockBean
    TaskResultService taskResultService;
    @MockBean
    InfoService infoService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModelWrapper modelWrapper;


    @Test
    public void setTaskResult_OK() throws Exception {
        when(this.taskResultService.setTaskResult(any(TaskResult.class))).thenReturn(this.taskResult);
        when(this.modelWrapper.getTaskResultFromTaskResultDto(any(TaskResultDto.class))).thenReturn(this.taskResult);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(this.dto);

        this.mvc.perform(post(this.path).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("file.pdf")));
    }

    @Test
    public void getTaskResult_OK() throws Exception {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskResultService.getTaskResult(anyLong(), anyString())).thenReturn(this.taskResult);

        long taskId = 1;

        this.mvc.perform(get(String.format("%s/%d", this.path, taskId))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("file.pdf")));
    }

    @Test
    public void getTaskResult_withoutHeader_BadRequest() throws Exception {
        when(this.taskResultService.getTaskResult(anyLong(), anyString())).thenReturn(this.taskResult);

        long taskId = 1;

        this.mvc.perform(get(String.format("%s/%d", this.path, taskId))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTaskResultsByTask_OK() throws Exception {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskResultService.getTaskResultsByTask(anyLong(), anyString())).thenReturn(new ArrayList<>());
        long taskId = 1;
        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/task/%d?page=%d&size=%d&sort=%s", this.path, taskId, page, size, sort))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTaskResultsByTask_withoutHeader_BadRequest() throws Exception {
        when(this.taskResultService.getTaskResultsByTask(anyLong(), anyString())).thenReturn(new ArrayList<>());
        long taskId = 1;
        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/task/%d?page=%d&size=%d&sort=%s", this.path, taskId, page, size, sort))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
