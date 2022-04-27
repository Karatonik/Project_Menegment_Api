package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.enums.AccessType;
import pl.project.ProjectManagement.model.enums.StatusType;
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

    @PutMapping("/des/{description}/{email}")
    public ResponseEntity<?> updateProjectDescription(@PathVariable @NotBlank String email,
                                                      @RequestBody @NotBlank Long projectId,
                                                      @PathVariable @NotBlank String description) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectDescription(email, projectId, description));
    }

    @PutMapping("/name/{name}/{email}")
    public ResponseEntity<?> updateProjectName(@PathVariable @NotBlank String email,
                                               @RequestBody @NotBlank Long projectId,
                                               @PathVariable @NotBlank String name) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectName(email, projectId, name));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable @NotBlank Long projectId) {

        return ResponseEntity.ok(new ProjectDto(this.projectService.getProject(projectId)));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<ProjectDto>> getProjects(@PathVariable @NotBlank String email) {

        return ResponseEntity.ok(this.projectService
                .getProjects(email).stream().map(ProjectDto::new).collect(Collectors.toList()));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteProject(@PathVariable @NotBlank String email,
                                           @NotBlank @RequestBody Long projectId) {

        return SmartResponseEntity.fromBoolean(this.projectService.deleteProject(email, projectId));
    }

    @PutMapping("/access/{access}/{email}")
    public ResponseEntity<?> updateProjectAccess(@PathVariable @NotBlank String email,
                                                 @RequestBody @NotBlank Long projectId,
                                                 @NotBlank @PathVariable AccessType access) {

        return SmartResponseEntity.fromBoolean(this.projectService.updateProjectAccess(email, projectId, access));
    }

    @PutMapping("/status/{status}/{email}")
    public ResponseEntity<?> updateProjectStatus(@PathVariable @NotBlank String email,
                                                 @RequestBody @NotBlank Long projectId,
                                                 @PathVariable @NotBlank StatusType status) {

        return SmartResponseEntity.fromBoolean(this.projectService.updateProjectStatus(email, projectId, status));
    }

}
