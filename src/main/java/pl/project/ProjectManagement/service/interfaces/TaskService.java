package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.Task;

import java.util.List;

public interface TaskService {

    Task setTask(Task task);

    Task getTask(Long taskId);

    List<Task> getProjectTasks(String email, Long projectId);

    List<Task> getTasks(String adminEmail, String token);
}
