package pl.project.ProjectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.ProjectManagement.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
