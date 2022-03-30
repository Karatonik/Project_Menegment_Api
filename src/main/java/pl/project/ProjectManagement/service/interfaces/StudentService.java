package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.StudyType;

import java.util.List;

public interface StudentService {

    Student setStudent(Student student);

    Student getStudent(String email);

    List<Student> getAllStudents(String adminEmail, String token);

    boolean updateStudentType(String email, StudyType studyType);

    boolean joinToProject(String email, Long projectId);

}
