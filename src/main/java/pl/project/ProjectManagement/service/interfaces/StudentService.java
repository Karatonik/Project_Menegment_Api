package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.StudyType;

import java.util.List;

public interface StudentService {

    Student setStudent(Student student);

    Student getStudent(String email);

    Page<Student> getAllStudents(String adminEmail, String token, Pageable pageable);

    boolean updateStudentType(String email, StudyType studyType);

    boolean joinToProject(String email, Long projectId);

}
