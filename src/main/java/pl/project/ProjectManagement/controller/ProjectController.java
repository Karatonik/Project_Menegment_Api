package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.request.DescriptionPayload;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;
import pl.project.ProjectManagement.model.request.ProjectAccessPayload;
import pl.project.ProjectManagement.model.request.ProjectNamePayload;
import pl.project.ProjectManagement.model.request.ProjectStatusPayload;
import pl.project.ProjectManagement.model.request.SecoundParent.WithProjectPayload;
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
    public ResponseEntity<?> setProject(ProjectDto projectDto) {
        projectDto = new ProjectDto(this.projectService.
                setProject(this.modelWrapper.getProjectFromDto(projectDto)));
        if (projectDto.equals(new ProjectDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(projectDto);
    }

    @PutMapping("/des")
    public ResponseEntity<?> updateProjectDescription(@RequestBody @NotBlank DescriptionPayload payload) {
        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectDescription(payload.getEmail(),
                        payload.getProjectId(), payload.getDescription()));
    }

    @PutMapping("/name")
    public ResponseEntity<?> updateProjectName(@RequestBody @NotBlank ProjectNamePayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectName(payload.getEmail(),
                        payload.getProjectId(), payload.getName()));
    }

    @GetMapping("/project")
    public ResponseEntity<?> getProject(@RequestBody @NotBlank ProjectPayload payload) {
        ProjectDto projectDto = new ProjectDto(this.projectService.getProject(payload.getProjectId()));
        if (projectDto.equals(new ProjectDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(projectDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestBody @NotBlank EmailPayload payload) {

        return ResponseEntity.ok(this.projectService
                .getProjects(payload.getEmail())
                .stream().map(ProjectDto::new).collect(Collectors.toList()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProject(@RequestBody @NotBlank WithProjectPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .deleteProject(payload.getEmail(), payload.getProjectId()));
    }

    @PutMapping("/access")
    public ResponseEntity<?> updateProjectAccess(@RequestBody @NotBlank ProjectAccessPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectAccess(payload.getEmail(), payload.getProjectId(),
                        payload.getAccess()));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateProjectStatus(@RequestBody @NotBlank ProjectStatusPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectStatus(payload.getEmail(), payload.getProjectId(),
                        payload.getStatus()));
    }

}
