package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;

import java.util.List;

public interface ProjectService {

    Project setProject(Project project);

    boolean updateProject(String email, Project project);

    Project getProject(Long projectId);

    Page<Project> getProjects(String email, Pageable pageable);

    List<Student> getStudentsWhoCanJoin(long projectId);

    boolean inviteStudentsToProject(List<Student> students , String email, long projectId);

    Page<Project> getProjectsToJoin(String email, Pageable pageable);

    boolean deleteProject(String email, Long projectId);

}
