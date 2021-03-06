package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.Role;
import pl.project.ProjectManagement.model.enums.StudyType;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {

    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;

    @Autowired
    public StudentServiceImp(StudentRepository studentRepository, ProjectRepository projectRepository,
                             PersonRepository personRepository) {
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Student setStudent(Student student) {
        return this.studentRepository.save(student);
    }

    @Override
    public Student getStudent(String email) {
        Optional<Student> optionalStudent = this.studentRepository.findById(email);
        return optionalStudent.orElseGet(Student::new);
    }

    @Override
    public Page<Student> getAllStudents(String adminEmail, String token, Pageable pageable) {
        Optional<Person> optionalPerson = personRepository.findByEmailAndToken(adminEmail, token);
        if (optionalPerson.isPresent()) {
            if (optionalPerson.get().getRole().equals(Role.ADMIN)) {
                return studentRepository.findAll(pageable);
            }
        }
        return Page.empty();
    }

    @Override
    public boolean updateStudentType(String email, StudyType studyType) {
        Optional<Student> optionalStudent = studentRepository.findById(email);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setStudyType(studyType);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    @Override
    public boolean joinToProject(String email, Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            if (project.getAccess().equals(AccessType.OPEN)) {
                Optional<Student> optionalStudent = studentRepository.findById(email);
                if (optionalStudent.isPresent()) {
                    System.out.println(optionalStudent.get());
                    List<Student> studentList = project.getStudents();
                    studentList = studentList.isEmpty() ? new ArrayList<>(): studentList;
                    studentList.add(optionalStudent.get());
                    project.setStudents(studentList);
                    System.out.println(project.getStudents());
                    projectRepository.save(project);
                    return true;
                }
            }
        }
        return false;
    }
}
