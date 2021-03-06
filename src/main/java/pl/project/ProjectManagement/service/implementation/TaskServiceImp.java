package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.repository.TaskRepository;
import pl.project.ProjectManagement.service.interfaces.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository, StudentRepository studentRepository,
                          ProjectRepository projectRepository, PersonRepository personRepository) {
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
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
    public Page<Task> getProjectTasks(String email, Long projectId, Pageable pageable) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            if (project.getProjectOwner().getEmail().equals(email)) {
                return taskRepository.findAllByProject(project,pageable);
            } else {
                Optional<Student> optionalStudent = studentRepository.findById(email);
                if (optionalStudent.isPresent()) {
                    if (project.getStudents().contains(optionalStudent.get())) {
                        return taskRepository.findAllByProject(project,pageable);
                    }

                }
            }
        }
        return Page.empty();
    }


    @Override
    public Page<Task> getTasks(String adminEmail, String token, Pageable pageable) {
        Optional<Person> optionalPerson = personRepository.findByEmailAndToken(adminEmail, token);
        if (optionalPerson.isPresent()) {
            if (optionalPerson.get().getRole().equals(Role.ADMIN)) {
                return taskRepository.findAll(pageable);
            }
        }
        return Page.empty();
    }
}
