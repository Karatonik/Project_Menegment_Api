package pl.project.ProjectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectIdAndProjectOwner(Long projectId, Person projectOwner);

    List<Project> findAllByProjectOwner(Person projectOwner);
}
