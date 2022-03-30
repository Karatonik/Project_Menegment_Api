package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.repository.TaskRepository;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp  implements TaskService { //todo

    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository, StudentRepository studentRepository,
                          ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Task setTask(Task task) {
        return this.taskRepository.save(task);
    }

    @Override
    public Task getTask(Long taskId) {
        Optional<Task> optionalTask = this.taskRepository.findById(taskId);
        return optionalTask.orElseGet(Task::new);
    }

    @Override
    public List<Task> getProjectTasks(String email, Long projectId) {
        return null;
    }

    @Override
    public List<Task> getTasks(String adminEmail, String token) {
        return null;
    }
}
