package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import pl.project.ProjectManagement.model.Task;
import org.springframework.data.domain.*;
import java.util.List;

public interface TaskService {

    Task setTask(Task task);

    Task getTask(Long taskId);

    Page<Task> getProjectTasks(String email, Long projectId, Pageable pageable);

    List<Task> getTasks(String adminEmail, String token);
}
