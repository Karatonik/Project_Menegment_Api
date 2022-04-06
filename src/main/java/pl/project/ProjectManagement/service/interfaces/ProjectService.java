package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;

import java.util.List;

public interface ProjectService {

    Project setProject(Project project);

    boolean updateProjectDescription(String email, Long projectId, String description);

    boolean updateProjectName(String email, Long projectId, String name);

    Project getProject(Long projectId);

    List<Project> getProjects(String email);

    boolean deleteProject(String email, Long projectId);

    boolean updateProjectAccess(String email, Long projectId, AccessType access);

    boolean updateProjectStatus(String email, Long projectId, StatusType status);
}
