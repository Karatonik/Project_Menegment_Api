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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.project.ProjectManagement.ProjectManagementApplication;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProjectManagementApplication.class})
@SpringBootTest
public class ProjectIntTests {

    private final String path = "/project";
    private final Person person = new Person("test@test.pl", "password123");

    private final Project project = new Project(1L, "Test", "Opis", LocalDateTime.now(), AccessType.OPEN, StatusType.CONTINUES, LocalDateTime.now(), LocalDate.now(), person, new ArrayList<>(), new ArrayList<>());
    private final ProjectDto dto = new ProjectDto(project);
    @MockBean
    ProjectService projectService;

    @MockBean
    InfoService infoService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModelWrapper modelWrapper;

    @Test
    public void setProject_OK() throws Exception {
        when(this.projectService.setProject(any())).thenReturn(this.project);
        when(this.modelWrapper.getProjectFromDto(this.dto)).thenReturn(this.project);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(this.project);

        this.mvc.perform(post(this.path).content(requestJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Test")));

    }

    @Test
    public void getProject_OK() throws Exception {
        when(this.projectService.getProject(anyLong())).thenReturn(this.project);

        this.mvc.perform(get(String.format("%s/project/%d", this.path, 1L)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Test")));
    }


    @Test
    public void getProjects_OK() throws Exception {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.projectService.getProjects(anyString(), any(Pageable.class))).thenReturn(Page.empty());

        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/list?page=%d&size=%d&sort=%s", this.path, page, size, sort)).header("Authorization", "Token").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getProjects_withoutHeader_BadRequest() throws Exception {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(projectService.getProjects(anyString(), any(Pageable.class))).thenReturn(Page.empty());

        int page = 0;
        int size = 12;
        String sort = "asc";

        this.mvc.perform(get(String.format("%s/list?page=%d&size=%d&sort=%s", this.path, page, size, sort)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteProject_OK() throws Exception {
        ProjectPayload projectPayload = new ProjectPayload(1L);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.projectService.deleteProject(anyString(), anyLong())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(projectPayload);

        this.mvc.perform(delete(String.format("%s/", this.path)).content(requestJson).header("Authorization", "Token").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("OK")));
    }

    @Test
    public void deleteProject_withoutHeader_BadRequest() throws Exception {
        ProjectPayload projectPayload = new ProjectPayload(1L);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.projectService.deleteProject(anyString(), anyLong())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(projectPayload);

        this.mvc.perform(delete(String.format("%s/", this.path)).content(requestJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteProject_BadRequest() throws Exception {
        ProjectPayload projectPayload = new ProjectPayload(1L);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.projectService.deleteProject(anyString(), anyLong())).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withoutRootName();
        String requestJson = ow.writeValueAsString(projectPayload);

        this.mvc.perform(delete(String.format("%s/", this.path)).content(requestJson).header("Authorization", "Token").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString("BAD_REQUEST")));
    }

}
