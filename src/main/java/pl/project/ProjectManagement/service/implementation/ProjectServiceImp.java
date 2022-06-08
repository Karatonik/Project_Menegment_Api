package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ProjectServiceImp(ProjectRepository projectRepository, StudentRepository studentRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Project setProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean updateProject(String email, Project project) {
        if(project.getProjectOwner().getEmail().equals(email)){
            projectRepository.save(project);
            return true;
        }
        return false;
    }


    @Override
    public Project getProject(Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        return optionalProject.orElseGet(Project::new);
    }

    @Override
    public Page<Project> getProjects(String email, Pageable pageable) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            if (person.getRole().equals(Role.ADMIN)) {
                return projectRepository.findAll(pageable);
            } else {
                Optional<Student> optionalStudent = studentRepository.findById(email);
                if (optionalStudent.isPresent()) {
                    Student student = optionalStudent.get();
                    System.out.println(projectRepository.findAllByProjectOwnerOrStudentsContains(person,student, pageable));

                    return projectRepository.findAllByProjectOwnerOrStudentsContains(person,student, pageable);
                }else{
                    return projectRepository.findAllByProjectOwner(person, pageable);
                }
            }
        }
        return Page.empty();
    }

    @Override
    public List<Student> getStudentsWhoCanJoin(long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            List<Student> students = studentRepository.findAll();
            students.removeAll(project.getStudents());

            return students;
        }
        return new ArrayList<>();
    }

    @Override
    public boolean inviteStudentsToProject(List<Student> students, String email, long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            if(project.getProjectOwner().getEmail().equals(email)){
                Set<Student> studentSet =  new HashSet<>(project.getStudents());
                studentSet.addAll(students);
                project.setStudents(studentSet.stream().toList());

            return  true;
            }
        }

        return false;
    }

    @Override
    public Page<Project> getProjectsToJoin(String email, Pageable pageable) {
       Optional<Person> optionalPerson = personRepository.findById(email);
       if(optionalPerson.isPresent()){
           Person person = optionalPerson.get();
           Optional<Student> optionalStudent = studentRepository.findById(email);
           if(optionalStudent.isPresent()){
               Student student = optionalStudent.get();
               return projectRepository.findAllByAccessAndProjectOwnerNotLikeOrStudentsNotContains(AccessType.OPEN
                       ,person,student, pageable);
           }else {
               return projectRepository.findAllByAccessAndProjectOwnerNotLike(AccessType.OPEN
                       ,person, pageable);
           }
       }
       return Page.empty();
    }

    @Override
    public boolean deleteProject(String email, Long projectId) {
        Optional<Person> optionalPerson = personRepository.findById(email);
        if (optionalPerson.isPresent()) {
            Optional<Project> optionalProject = projectRepository.findByProjectIdAndProjectOwner(projectId, optionalPerson.get());
            if (optionalProject.isPresent()) {
                projectRepository.delete(optionalProject.get());
                return true;
            }

        }
        return false;
    }

}
