package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.enums.AccessTyp;
import pl.project.ProjectManagement.model.enums.StatusType;

import java.util.List;

public interface ProjectService {

    Project setProject(Project project);

    Project setProject(String name, String description);

    boolean updateProjectDescription(String email, Long projectId, String description);

    boolean updateProjectName(String email, Long projectId, String name);

    Project getProject(Long projectId);

    List<Project> getProjects(String email);

    boolean deleteProject(String email, Long projectId);

    boolean updateProjectAccess(String email, Long projectId, AccessTyp access);

    boolean updateProjectStatus(String email, Long projectId, StatusType status);
}
