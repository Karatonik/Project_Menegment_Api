package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.ProjectController;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.ProjectService;
import pl.project.ProjectManagement.model.enums.AccessType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static pl.project.ProjectManagement.model.enums.AccessType.OPEN;

@ExtendWith(MockitoExtension.class)
public class ProjectUnitTests {

    private final EmailAndPassword ep = new EmailAndPassword("test@test.com", "password123");

    private final Project project = new Project("TEST", "OPIS", LocalDateTime.now(), LocalDateTime.now(), LocalDate.of(2021, 6, 7), any(), any(), any());
    @Mock
    ProjectService projectService;
    @InjectMocks
    ProjectController projectController;

    @Test
    public void setProject_OK() {
        when(projectService.setProject(any())).thenReturn(project);

        ResponseEntity<?> response = projectController.setProject(project);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void setProject_BAD() {
        when(projectService.setProject(any())).thenReturn(project);

        ResponseEntity<?> response = projectController.setProject(project);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }

    @Test
    public void updateProjectDescription_OK() {
        when(projectService.updateProjectDescription(anyString(), anyLong(), anyString())).thenReturn(true);

        ResponseEntity<?> response = projectController.updateProjectDescription("test@test.pl", 1L, "TESTOWY OPIS");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateProjectDescription_BAD() {
        when(projectService.updateProjectDescription(anyString(), anyLong(), anyString())).thenReturn(false);

        ResponseEntity<?> response = projectController.updateProjectDescription("test@@test.pl", -1L, null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void updateProjectName_OK() {
        when(projectService.updateProjectName(anyString(), anyLong(), anyString())).thenReturn(true);

        ResponseEntity<?> response = projectController.updateProjectName("test@test.pl", 2L, "NAZWA2");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateProjectName_BAD() {
        when(projectService.updateProjectName(anyString(), anyLong(), anyString())).thenReturn(false);

        ResponseEntity<?> response = projectController.updateProjectName("test@@.pl", -2L, "NAZWA2");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getProject_OK() {
        when(projectService.getProject(anyLong())).thenReturn(project);

        ResponseEntity<?> response = projectController.getProject(2L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getProject_BAD() {
        when(projectService.getProject(anyLong())).thenReturn(project);

        ResponseEntity<?> response = projectController.getProject(-2L);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getProjects_OK() {
        when(projectService.getProjects(anyString())).thenReturn(true);

        ResponseEntity<?> response = projectController.getProjects("test@test.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getProjects_BAD() {
        when(projectService.getProjects(anyString())).thenReturn(false);

        ResponseEntity<?> response = projectController.getProjects("test@@@.tatatata");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void deleteProject_OK() {
        when(projectService.deleteProject(anyString(), anyLong())).thenReturn(true);

        ResponseEntity<?> response = projectController.deleteProject("test@test.pl", 1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void deleteProject_BAD() {
        when(projectService.deleteProject(anyString(), anyLong())).thenReturn(false);

        ResponseEntity<?> response = projectController.deleteProject("test@@.pl", -1L);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void updateProjectAccess_OK() {
        when(projectService.updateProjectAccess(anyString(), anyLong(), any())).thenReturn(true);

        ResponseEntity<?> response = projectController.updateProjectAccess("testowy@edu.pl", 3L, OPEN);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateProjectAccess_BAD() {
        when(projectService.updateProjectAccess(anyString(), anyLong(), any())).thenReturn(false);

        ResponseEntity<?> response = projectController.updateProjectAccess("testowy@@.pl", -3L, null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void updateProjectStatus_OK() {
        when(projectService.updateProjectStatus(anyString(), anyLong(), any())).thenReturn(true);

        ResponseEntity<?> response = projectController.updateProjectStatus("nowy@eu.ep", 8L, StatusType.CLOSE);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateProjectStatus_BAD() {
        when(projectService.updateProjectStatus(anyString(), anyLong(), any())).thenReturn(false);

        ResponseEntity<?> response = projectController.updateProjectStatus("nowy@eu.epsss", -8L, null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}