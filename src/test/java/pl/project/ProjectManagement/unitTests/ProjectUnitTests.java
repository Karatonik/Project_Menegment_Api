package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.ProjectController;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectUnitTests {

    private final Person person = new Person("test@test.pl", "password123");
    private final Project project = new Project("TEST"
            , "OPIS", LocalDateTime.now(), LocalDateTime.now()
            , LocalDate.of(2021, 6, 7), new ArrayList<>(), new ArrayList<>(), person);
    private final ProjectDto dto = new ProjectDto(project);
    @Mock
    ProjectService projectService;
    @Mock
    InfoService infoService;

    @Mock
    ModelWrapper modelWrapper;
    @InjectMocks
    ProjectController projectController;

    @Test
    public void setProject_OK() {
        when(this.projectService.setProject(any())).thenReturn(this.project);
        when(this.modelWrapper.getProjectFromDto(any(ProjectDto.class))).thenReturn(this.project);

        ResponseEntity<?> response = this.projectController.setProject(this.dto);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }


    @Test
    public void getProject_OK() {
        when(projectService.getProject(anyLong())).thenReturn(project);

        ResponseEntity<?> response = projectController.getProject(2L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }


    @Test
    public void getProjects_OK() {
        when(projectService.getProjects(anyString(), any(Pageable.class))).thenReturn(Page.empty());
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        Pageable pageable = PageRequest.of(0, 12);

        ResponseEntity<?> response = projectController.getProjects("test@test.pl", pageable);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void deleteProject_OK() {
        ProjectPayload projectPayload = new ProjectPayload(1L);
        when(projectService.deleteProject(anyString(), anyLong())).thenReturn(true);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ResponseEntity<?> response = projectController.deleteProject("test@test.pl", projectPayload);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void deleteProject_BadRequest() {
        ProjectPayload projectPayload = new ProjectPayload(1L);
        when(projectService.deleteProject(anyString(), anyLong())).thenReturn(false);
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");

        ResponseEntity<?> response = projectController.deleteProject("test@test.pl", projectPayload);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

}
