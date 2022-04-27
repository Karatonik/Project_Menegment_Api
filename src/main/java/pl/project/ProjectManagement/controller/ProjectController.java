package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.request.*;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", maxAge = 7200)
public class ProjectController {

    private final ProjectService projectService;
    private final ModelWrapper modelWrapper;

    @Autowired
    public ProjectController(ProjectService projectService, ModelWrapper modelWrapper) {
        this.projectService = projectService;
        this.modelWrapper = modelWrapper;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> setProject(ProjectDto projectDTO) {
        return ResponseEntity.ok(new ProjectDto(this.projectService.
                setProject(this.modelWrapper.getProjectFromDto(projectDTO))));
    }

    @PutMapping("/des")
    public ResponseEntity<?> updateProjectDescription(@RequestBody DescriptionPayload descriptionPayload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectDescription(descriptionPayload.getEmail(),
                        descriptionPayload.getProjectId(), descriptionPayload.getDescription()));
    }

    @PutMapping("/name")
    public ResponseEntity<?> updateProjectName(@RequestBody ProjectNamePayload projectNamePayload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectName(projectNamePayload.getEmail(),
                        projectNamePayload.getProjectId(), projectNamePayload.getName()));
    }

    @GetMapping("/project")
    public ResponseEntity<ProjectDto> getProject(@RequestBody @NotBlank Long projectId) {

        return ResponseEntity.ok(new ProjectDto(this.projectService.getProject(projectId)));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestBody @NotBlank String email) {

        return ResponseEntity.ok(this.projectService
                .getProjects(email).stream().map(ProjectDto::new).collect(Collectors.toList()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProject(@RequestBody ProjectDeletePayload projectDeletePayload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .deleteProject(projectDeletePayload.getEmail(), projectDeletePayload.getProjectId()));
    }

    @PutMapping("/access")
    public ResponseEntity<?> updateProjectAccess(@RequestBody ProjectAccessPayload projectAccessPayload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectAccess(projectAccessPayload.getEmail(), projectAccessPayload.getProjectId(),
                        projectAccessPayload.getAccess()));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateProjectStatus(@RequestBody ProjectStatusPayload projectStatusPayload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectStatus(projectStatusPayload.getEmail(), projectStatusPayload.getProjectId(),
                        projectStatusPayload.getStatus()));
    }

}
