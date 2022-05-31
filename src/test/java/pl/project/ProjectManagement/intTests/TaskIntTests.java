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
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProjectManagementApplication.class})
@SpringBootTest
public class TaskIntTests {

    private final String path = "/task";
    private final Person person = new Person("test@test.pl", "password123");
    private final Project project = new Project(1L, "Test", "Opis", LocalDateTime.now(),
            AccessType.OPEN, StatusType.CONTINUES, LocalDateTime.now(), LocalDate.now(), this.person, new ArrayList<>(), new ArrayList<>());
    private final Task task = new Task(1L, "Test", 1, "Opis", LocalDateTime.now(), this.project, new ArrayList<>());
    private final TaskDto dto = new TaskDto(this.task);
    @MockBean
    TaskService taskService;
    @MockBean
    InfoService infoService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModelWrapper modelWrapper;

    @Test
    public void setTask_OK() throws Exception {
        when(this.taskService.setTask(any(Task.class))).thenReturn(this.task);
        when(this.modelWrapper.getTaskFromDto(any(TaskDto.class))).thenReturn(this.task);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.dto);

        this.mvc.perform(post(this.path).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));
    }

    @Test
    public void getTask_OK() throws Exception {
        when(taskService.getTask(anyLong())).thenReturn(this.task);
        long taskId = 1;

        mvc.perform(get(String.format("%s/%d", this.path, taskId))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));
    }

/*
    @Test
    public void getProjectTasks_OK() throws Exception {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskService.getProjectTasks(anyString(), any())).thenReturn(new ArrayList<>());
        long projectId = 1;
        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/project/%d?page=%d&size=%d&sort=%s",
                        this.path, projectId, page, size, sort))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectTasks_withoutHeader_BadRequest() throws Exception {
        when(this.taskService.getProjectTasks(anyString(), any())).thenReturn(new ArrayList<>());
        long projectId = 1;
        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/project/%d?page=%d&size=%d&sort=%s",
                        this.path, projectId, page, size, sort))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
*/
    @Test
    public void getTasks_OK() throws Exception {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskService.getTasks(anyString(), any())).thenReturn(new ArrayList<>());

        String token = "token";
        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/all/%s?page=%d&size=%d&sort=%s", this.path, token, page, size, sort))
                        .header("Authorization", "Token")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTasks_withoutHeader_BadRequest() throws Exception {
        when(this.taskService.getTasks(anyString(), any())).thenReturn(new ArrayList<>());

        String token = "token";
        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/all/%s?page=%d&size=%d&sort=%s", this.path, token, page, size, sort))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
