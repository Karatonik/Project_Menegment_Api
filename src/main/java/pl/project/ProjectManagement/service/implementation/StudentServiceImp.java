package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.StudyType;
import pl.project.ProjectManagement.repository.PersonRepository;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {//todo

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
    public List<Student> getAllStudents(String adminEmail, String token) {
        return null;
    }

    @Override
    public boolean updateStudentType(String email, StudyType studyType) {
        return false;
    }

    @Override
    public boolean joinToProject(String email, Long projectId) {
        return false;
    }
}
