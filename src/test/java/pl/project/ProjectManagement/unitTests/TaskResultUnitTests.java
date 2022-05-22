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
import pl.project.ProjectManagement.controller.TaskResultController;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static pl.project.ProjectManagement.model.enums.StudyType.STATIONARY;

@ExtendWith(MockitoExtension.class)
public class TaskResultUnitTests {
    private final Person person = new Person("test@test.pl", "password123");
    private final Student student = new Student("test@test.pl", "Jan", "Kowalski", "11111", STATIONARY, Collections.emptySet(), new ArrayList<>(), this.person);
    private final Project project = new Project(1L, "Test", "Opis", LocalDateTime.now(), AccessType.OPEN, StatusType.CONTINUES, LocalDateTime.now(), LocalDate.now(), this.person, new ArrayList<>(), new ArrayList<>());
    private final Task task = new Task(1L, "Test", 1, "Opis", LocalDateTime.now(), this.project, new ArrayList<>());
    private final TaskResult taskResult = new TaskResult(1L, this.student, this.task, "file.pdf", LocalDateTime.now());
    private final TaskResultDto dto = new TaskResultDto(this.taskResult);
    @Mock
    TaskResultService taskResultService;
    @Mock
    InfoService infoService;
    @InjectMocks
    TaskResultController taskResultController;
    @Mock
    private ModelWrapper modelWrapper;

    @Test
    public void setTaskResult_OK() {
        when(this.taskResultService.setTaskResult(any(TaskResult.class))).thenReturn(this.taskResult);
        when(this.modelWrapper.getTaskResultFromTaskResultDto(any(TaskResultDto.class))).thenReturn(this.taskResult);

        ResponseEntity<?> response = taskResultController.setTaskResult(this.dto);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTaskResult_OK() {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskResultService.getTaskResult(anyLong(), anyString())).thenReturn(this.taskResult);
        long taskId = 1;

        ResponseEntity<?> response = taskResultController.getTaskResult("test@test.pl", taskId);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getTaskResultsByTask_OK() {
        when(this.infoService.getEmailFromJwt(anyString())).thenReturn("test@test.pl");
        when(this.taskResultService.getTaskResultsByTask(anyLong(), anyString())).thenReturn(new ArrayList<>());

        long taskId = 1;
        Pageable pageable = PageRequest.of(0, 12);
        int size = 10;

        ResponseEntity<?> response = taskResultController.getTaskResultsByTask("test@test.pl", taskId, pageable, size);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

}
