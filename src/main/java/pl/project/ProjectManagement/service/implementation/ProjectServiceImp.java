package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.enums.AccessTyp;
import pl.project.ProjectManagement.model.enums.StatusType;
import pl.project.ProjectManagement.repository.ProjectRepository;
import pl.project.ProjectManagement.repository.StudentRepository;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImp implements ProjectService {//todo

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ProjectServiceImp(ProjectRepository projectRepository, StudentRepository studentRepository) {
        this.projectRepository = projectRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Project setProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project setProject(String name, String description) {
        return null;
    }

    @Override
    public boolean updateProjectDescription(String email, Long projectId, String description) {
        return false;
    }

    @Override
    public boolean updateProjectName(String email, Long projectId, String name) {
        return false;
    }

    @Override
    public Project getProject(Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        return optionalProject.orElseGet(Project::new);
    }
    @Override
    public List<Project> getProjects(String email) {
        return null;
    }

    @Override
    public boolean deleteProject(String email, Long projectId) {
        return false;
    }

    @Override
    public boolean updateProjectAccess(String email, Long projectId, AccessTyp access) {
        return false;
    }

    @Override
    public boolean updateProjectStatus(String email, Long projectId, StatusType status) {
        return false;
    }
}
