package pl.project.ProjectManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.ProjectManagement.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
