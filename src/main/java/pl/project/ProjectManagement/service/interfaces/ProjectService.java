package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;

public interface ProjectService {

    Project setProject(Project project);

    boolean updateProjectDescription(String email, Long projectId, String description);

    boolean updateProjectName(String email, Long projectId, String name);

    Project getProject(Long projectId);

    Page<Project> getProjects(String email, Pageable pageable);

    Page<Project> getProjectsToJoin(String email, Pageable pageable);

    boolean deleteProject(String email, Long projectId);

    boolean updateProjectAccess(String email, Long projectId, AccessType access);

    boolean updateProjectStatus(String email, Long projectId, StatusType status);
}
