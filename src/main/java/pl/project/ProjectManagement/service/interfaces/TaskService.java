package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.project.ProjectManagement.model.Task;

import java.util.List;

public interface TaskService {

    Task setTask(Task task);

    Task getTask(Long taskId);

    Page<Task> getProjectTasks(String email, Long projectId, Pageable pageable);

    Page<Task> getTasks(String adminEmail, String token, Pageable pageable);
}
