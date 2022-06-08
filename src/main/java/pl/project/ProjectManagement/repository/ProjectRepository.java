package pl.project.ProjectManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.AccessType;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectIdAndProjectOwner(Long projectId, Person projectOwner);

    Page<Project> findAllByProjectOwner(Person projectOwner,  Pageable pageable);
    Page<Project> findAllByProjectOwnerOrStudentsContains(Person projectOwner, Student student, Pageable pageable);

    Page<Project> findAllByAccessAndProjectOwnerNotLikeOrStudentsNotContains(AccessType accessType, Person person
            , Student student, Pageable pageable);

    Page<Project> findAllByAccessAndProjectOwnerNotLike(AccessType accessType, Person person
           , Pageable pageable);
}
