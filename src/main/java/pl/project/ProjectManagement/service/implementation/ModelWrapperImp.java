package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.*;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.dto.TaskDto;
import pl.project.ProjectManagement.model.dto.TaskResultDto;
import pl.project.ProjectManagement.repository.*;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ModelWrapperImp implements ModelWrapper {


    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    private final TaskResultRepository taskResultRepository;

    @Autowired
    public ModelWrapperImp(ProjectRepository projectRepository, StudentRepository studentRepository, PersonRepository personRepository, TaskRepository taskRepository, TaskResultRepository taskResultRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
        this.taskResultRepository = taskResultRepository;
    }

    @Override
    public Project getProjectFromDto(ProjectDto dto) {
        Project project = new Project();

        if (dto.getProjectId() != null) {
            Optional<Project> optionalProject = this.projectRepository.findById(dto.getProjectId());
            project = optionalProject.orElse(new Project());
        }
        Optional<Person> optionalPerson = this.personRepository.findById(dto.getProjectOwnerEmail());

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
        Student student = new Student();
        if (dto.getPersonEmail() != null) {
            Optional<Person> optionalPerson = this.personRepository.findById(dto.getPersonEmail());
            student.setPerson(optionalPerson.orElse(new Person()));
        }
        student.setEmail(dto.getEmail());
        student.setName(dto.getName());
        student.setSurname(dto.getSurname());
        student.setIndex_number(dto.getIndex_number());
        student.setStudyType(dto.getStudyType());
        student.setProjects(new HashSet<>(this.projectRepository.findAllById(dto.getProjectsIds())));

        return student;
    }

    @Override
    public Task getTaskFromDto(TaskDto dto) {
        Task task = new Task();
        if (dto.getTaskId() != null) {
            Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
            task = optionalTask.orElse(new Task());
        }
        if (dto.getProjectIds() != null) {
            Optional<Project> optionalProject = projectRepository.findById(dto.getProjectIds());
            task.setProject(optionalProject.orElse(new Project()));
        }
        task.setDescription(dto.getDescription());
        task.setName(dto.getName());
        task.setOrderNumber(dto.getOrderNumber());
        task.setDateTimeAdded(dto.getDateTimeAdded());
        return task;
    }

    @Override
    public TaskResult getTaskResultFromTaskResultDto(TaskResultDto dto) {
        TaskResult taskResult;
        Optional<TaskResult> optionalTaskResult = taskResultRepository.findById(dto.getResultId());
        Optional<Student> optionalStudent = studentRepository.findById(dto.getStudentEmail());
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());

        taskResult = optionalTaskResult.orElse(new TaskResult());
        taskResult.setTask(optionalTask.orElse(new Task()));
        taskResult.setStudent(optionalStudent.orElse(new Student()));

        taskResult.setDateOfUpload(dto.getDateOfUpload());
        taskResult.setFileName(dto.getFileName());

        return taskResult;
    }
}
