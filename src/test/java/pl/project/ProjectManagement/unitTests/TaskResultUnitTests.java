package pl.project.ProjectManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.project.ProjectManagement.controller.TaskResultController;
import pl.project.ProjectManagement.model.request.EmailAndPassword;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskResultUnitTests {

    @Mock
    TaskResultService taskResultService;

    @InjectMocks
    TaskResultController taskResultController;


    @Test
    public void setTaskResult_shouldContainStatus_OK() {
        when(taskResultService.setTaskResult(any())).thenReturn(true);

        ResponseEntity<?> response = taskResultController.setTaskResult();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setTaskResult_shouldContainStatus_BAD_REQUEST() {
        when(taskResultService.setTaskResult(any())).thenReturn(false);

        ResponseEntity<?> response = taskResultController.setTaskResult();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getTaskResult_shouldContainStatus_OK() {
        when(taskResultService.getTaskResult(any())).thenReturn(true);

        ResponseEntity<?> response = taskResultController.getTaskResult(1L,"test@test.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTaskResult_shouldContainStatus_BAD_REQUEST() {
        when(taskResultService.getTaskResult(anyString())).thenReturn(false);

        ResponseEntity<?> response = taskResultController.getTaskResult(-2L, "a@@.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getTaskResultsByTask_shouldContainStatus_OK() {
        when(taskResultService.getTaskResultsByTask(any(), anyString())).thenReturn(true);

        ResponseEntity<?> response = taskResultController.getTaskResultsByTask(1L,"test@test.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTaskResultsByTask_shouldContainStatus_OK() {
        when(taskResultService.getTaskResultsByTask(any(), anyString())).thenReturn(false);

        ResponseEntity<?> response = taskResultController.getTaskResultsByTask(-1L,"test@@test.pl");

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }




}
