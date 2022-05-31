package pl.project.ProjectManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Task;

import org.springframework.data.domain.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> getTasksByProject (Project project, Pageable pageable);
}
