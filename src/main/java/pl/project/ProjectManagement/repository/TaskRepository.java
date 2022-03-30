package pl.project.ProjectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.ProjectManagement.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
