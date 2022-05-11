package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import javax.validation.Valid;
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
    public ResponseEntity<?> setProject(@RequestBody ProjectDto projectDto) {
        projectDto = new ProjectDto(this.projectService.
                setProject(this.modelWrapper.getProjectFromDto(projectDto)));
        if (projectDto.equals(new ProjectDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(projectDto);
    }

    @PutMapping("/des")
    public ResponseEntity<?> updateProjectDescription(@RequestBody @Valid DescriptionPayload payload) {
        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectDescription(payload.getEmail(),
                        payload.getProjectId(), payload.getDescription()));
    }

    @PutMapping("/name")
    public ResponseEntity<?> updateProjectName(@RequestBody @Valid ProjectNamePayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectName(payload.getEmail(),
                        payload.getProjectId(), payload.getName()));
    }

    @GetMapping("/project")
    public ResponseEntity<?> getProject(@RequestBody @Valid ProjectPayload payload) {
        ProjectDto projectDto = new ProjectDto(this.projectService.getProject(payload.getProjectId()));
        if (projectDto.equals(new ProjectDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(projectDto);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProjects(@RequestBody @Valid EmailPayload payload,
                                         Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.projectService
                .getProjects(payload.getEmail())
                .stream().map(ProjectDto::new).collect(Collectors.toList()), pageable, size));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProject(@RequestBody @Valid WithProjectPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .deleteProject(payload.getEmail(), payload.getProjectId()));
    }

    @PutMapping("/access")
    public ResponseEntity<?> updateProjectAccess(@RequestBody @Valid ProjectAccessPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectAccess(payload.getEmail(), payload.getProjectId(),
                        payload.getAccess()));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateProjectStatus(@RequestBody @Valid ProjectStatusPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectStatus(payload.getEmail(), payload.getProjectId(),
                        payload.getStatus()));
    }

}
