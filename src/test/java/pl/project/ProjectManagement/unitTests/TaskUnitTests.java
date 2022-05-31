package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.TaskController;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskUnitTests {

    private final Person person = new Person("test@test.pl", "password123");
    private final Project project = new Project(1L, "Test", "Opis", LocalDateTime.now(),
            AccessType.OPEN, StatusType.CONTINUES, LocalDateTime.now(), LocalDate.now(), this.person, new ArrayList<>(), new ArrayList<>());
    private final Task task = new Task(1L, "Test", 1, "Opis", LocalDateTime.now(), this.project, new ArrayList<>());
    private final TaskDto dto = new TaskDto(this.task);
    @Mock
    TaskService taskService;
    @Mock
    InfoService infoService;
    @Mock
    ModelWrapper modelWrapper;
    @InjectMocks
    TaskController taskController;


    @Test
    public void setTask_OK() {
        when(this.taskService.setTask(any(Task.class))).thenReturn(this.task);
        when(this.modelWrapper.getTaskFromDto(any(TaskDto.class))).thenReturn(this.task);

        ResponseEntity<?> response = taskController.setTask(this.dto);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTask_OK() {
        when(taskService.getTask(anyLong())).thenReturn(this.task);
        long taskId = 1;

        ResponseEntity<?> response = taskController.getTask(1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getProjectTasks_OK() {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskService.getProjectTasks(anyString(), any())).thenReturn(new ArrayList<>());
        long projectId = 1;
        Pageable pageable = PageRequest.of(0, 12);

        ResponseEntity<?> response = taskController.getProjectTasks("test@test.pl", projectId, pageable);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }


    @Test
    public void getTasks_OK() {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskService.getTasks(anyString(), any())).thenReturn(new ArrayList<>());
        String token = "token";
        Pageable pageable = PageRequest.of(0, 12);
        int size = 12;

        ResponseEntity<?> response = taskController.getTasks("test@test.pl", token, pageable, size);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

}
