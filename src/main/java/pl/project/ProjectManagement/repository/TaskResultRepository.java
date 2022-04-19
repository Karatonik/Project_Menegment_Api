package pl.project.ProjectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.TaskResult;

import java.util.List;
import java.util.Optional;

public interface TaskResultRepository  extends JpaRepository<TaskResult, Long> {
    Optional<TaskResult> findByFileName( String fileName);
    List<TaskResult> findAllByTask(Task task);
}
