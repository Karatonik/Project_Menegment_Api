package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.repository.TaskRepository;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ModelWrapperImp implements ModelWrapper {


    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ModelWrapperImp(ProjectRepository projectRepository,
                           StudentRepository studentRepository,
                           PersonRepository personRepository,
                           TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Project getProjectFromDto(ProjectDto dto) {
        Project project;
        Optional<Project> optionalProject = this.projectRepository.findById(dto.getProjectId());
        Optional<Person> optionalPerson = this.personRepository.findById(dto.getProjectOwnerEmail());

        project = optionalProject.orElse(new Project());
        project.setProjectId(dto.getProjectId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setDataAndTimeOfCreation(dto.getDataAndTimeOfCreation());
        project.setAccess(dto.getAccess());
        project.setStatus(dto.getStatus());
        project.setDateOfDelivery(dto.getDateOfDelivery());
        project.setProjectOwner(optionalPerson.orElse(new Person()));
        project.setTasks(this.taskRepository.findAllById(dto.getTasksIds()));
        project.setStudents(this.studentRepository.findAllById(dto.getStudentsEmails()));

        return project;
    }

    @Override
    public Student getStudentFromDto(StudentDto dto) {
        Optional<Person> optionalPerson = this.personRepository.findById(dto.getPersonEmail());

        Student student = new Student();
        student.setEmail(dto.getEmail());
        student.setSurname(dto.getSurname());
        student.setIndex_number(dto.getIndex_number());
        student.setStudyType(dto.getStudyType());
        student.setPerson(optionalPerson.orElse(new Person()));
        student.setProjects(new HashSet<>(this.projectRepository.findAllById(dto.getProjectsIds())));

        return student;
    }

    @Override
    public Task getTaskFromDto(TaskDto dto) {
        Task task;
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectIds());

        task = optionalTask.orElse(new Task());
        task.setName(dto.getName());
        task.setOrderNumber(dto.getOrderNumber());
        task.setDateTimeAdded(dto.getDateTimeAdded());
        task.setProject(optionalProject.orElse(new Project()));

        return task;
    }
}
