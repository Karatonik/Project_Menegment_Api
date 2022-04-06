package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImp implements ProjectService {//todo

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ProjectServiceImp(ProjectRepository projectRepository,
                             StudentRepository studentRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Project setProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean updateProjectDescription(String email, Long projectId, String description) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            Optional<Project> optionalProject = projectRepository
                    .findByProjectIdAndProjectOwner(projectId, person);
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                project.setDescription(description);
                projectRepository.save(project);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateProjectName(String email, Long projectId, String name) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            Optional<Project> optionalProject = projectRepository
                    .findByProjectIdAndProjectOwner(projectId, person);
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                project.setName(name);
                projectRepository.save(project);
                return true;
            }
        }
        return false;
    }

    @Override
    public Project getProject(Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        return optionalProject.orElseGet(Project::new);
    }

    @Override
    public List<Project> getProjects(String email) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            if (person.getRole().equals(Role.Admin)) {
                return projectRepository.findAll();
            } else {
                Optional<Student> optionalStudent = studentRepository.findById(email);
                List<Project> projectList = new ArrayList<>(person.getOwnedProjects());
                if (optionalStudent.isPresent()) {
                    Student student = optionalStudent.get();
                    projectList.addAll(student.getProjects());
                    projectList = new ArrayList<>(new HashSet<>(projectList));
                }
                return projectList;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteProject(String email, Long projectId) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Optional<Project> optionalProject = projectRepository
                    .findByProjectIdAndProjectOwner(projectId, optionalPerson.get());
            if (optionalProject.isPresent()) {
                projectRepository.delete(optionalProject.get());
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean updateProjectAccess(String email, Long projectId, AccessType access) {
     Optional<Person> optionalPerson = personRepository.findById(email);
     if(optionalPerson.isPresent()){
         Optional<Project> optionalProject = projectRepository
                 .findByProjectIdAndProjectOwner(projectId, optionalPerson.get());
         if(optionalProject.isPresent()){
             Project project = optionalProject.get();
             project.setAccess(access);
             projectRepository.save(project);
             return  true;
         }
     }
        return false;
    }

    @Override
    public boolean updateProjectStatus(String email, Long projectId, StatusType status) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if(optionalPerson.isPresent()){
            Optional<Project> optionalProject = projectRepository
                    .findByProjectIdAndProjectOwner(projectId, optionalPerson.get());
            if(optionalProject.isPresent()){
                Project project = optionalProject.get();
                project.setStatus(status);
                projectRepository.save(project);
                return  true;
            }
        }
        return false;
    }
}
