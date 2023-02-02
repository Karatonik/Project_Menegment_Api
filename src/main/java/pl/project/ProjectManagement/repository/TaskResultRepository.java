package pl.project.ProjectManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.TaskResult;

import java.util.List;
import java.util.Optional;

public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {
    Optional<TaskResult> findByFileName(String fileName);

    Page<TaskResult> findAllByTask(Task task, Pageable pageable);
    List<TaskResult> findAllByTask(Task task);
}
