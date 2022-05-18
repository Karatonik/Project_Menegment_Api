package pl.project.ProjectManagement.unitTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.TaskController;
import pl.project.ProjectManagement.controller.TaskResultController;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskUnitTests {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;


    @Test
    public void setTask_shouldContainStatus_OK() {
        when(taskService.setTask(any())).thenReturn(true);

        ResponseEntity<?> response = taskController.setTask();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setTask_shouldContainStatus_BAD_REQUEST() {
        when(taskService.setTask(any())).thenReturn(false);

        ResponseEntity<?> response = taskController.setTask();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getTask_shouldContainStatus_OK(){
        when(taskService.getTask(any())).thenReturn(true);

        ResponseEntity<?> response = taskController.getTask(1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTask_shouldContainStatus_BAD_REQUEST(){
        when(taskService.getTask(any())).thenReturn(false);

        ResponseEntity<?> response = taskController.getTask(-2L);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getProjectTasks_shouldContainStatus_OK(){
        when(taskService.getProjectTasks(anyString(), any())).thenReturn(true);

        ResponseEntity<?> response = taskController.getProjectTasks("test@test.pl", 1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getProjectTasks_shouldContainStatus_BAD_REQUEST(){
        when(taskService.getProjectTasks(anyString(), any())).thenReturn(false);

        ResponseEntity<?> response = taskController.getProjectTasks("a@@.pl", -5L);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getTasks_shouldContainStatus_OK(){
        when(taskService.getTasks(anyString(), any())).thenReturn(true);

        ResponseEntity<?> response = taskController.getTasks("admin1@aa.pl", any());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTasks_shouldContainStatus_BAD_REQUEST(){
        when(taskService.getTasks(anyString(), any())).thenReturn(false);

        ResponseEntity<?> response = taskController.getTasks("a@@.pl", any());

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

}
