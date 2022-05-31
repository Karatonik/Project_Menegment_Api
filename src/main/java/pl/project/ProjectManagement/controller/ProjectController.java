package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.request.DescriptionPayload;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;
import pl.project.ProjectManagement.model.request.ProjectAccessPayload;
import pl.project.ProjectManagement.model.request.ProjectNamePayload;
import pl.project.ProjectManagement.model.request.ProjectStatusPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.InfoService;
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
    private final InfoService infoService;


    @Autowired
    public ProjectController(ProjectService projectService, ModelWrapper modelWrapper, InfoService infoService) {
        this.projectService = projectService;
        this.modelWrapper = modelWrapper;
        this.infoService = infoService;
    }

    @PostMapping
    public ResponseEntity<?> setProject(@RequestBody @Valid ProjectDto projectDto) {
        return ResponseEntity.ok(new ProjectDto(this.projectService
                .setProject(this.modelWrapper.getProjectFromDto(projectDto))));
    }

    @PutMapping("/des")
    public ResponseEntity<?> updateProjectDescription(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid DescriptionPayload payload) {
        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectDescription(this.infoService.getEmailFromJwt(authorization)
                        , payload.getProjectId(), payload.getDescription()));
    }

    @PutMapping("/name")
    public ResponseEntity<?> updateProjectName(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid ProjectNamePayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectName(this.infoService.getEmailFromJwt(authorization)
                        , payload.getProjectId(), payload.getName()));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable long projectId) {
        return ResponseEntity.ok(new ProjectDto(this.projectService.getProject(projectId)));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProjects(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        Page<Project> projectPage = this.projectService.getProjects(this.infoService
                .getEmailFromJwt(authorization), pageable);

        return ResponseEntity.ok(new PageImpl<>(projectPage.stream().map(ProjectDto::new)
                .collect(Collectors.toList()), pageable, projectPage.getTotalElements()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProject(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid ProjectPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .deleteProject(this.infoService.getEmailFromJwt(authorization), payload.getProjectId()));
    }

    @PutMapping("/access")
    public ResponseEntity<?> updateProjectAccess(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid ProjectAccessPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectAccess(this.infoService.getEmailFromJwt(authorization)
                        , payload.getProjectId(), payload.getAccess()));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateProjectStatus(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid ProjectStatusPayload payload) {

        return SmartResponseEntity.fromBoolean(this.projectService
                .updateProjectStatus(this.infoService.getEmailFromJwt(authorization)
                        , payload.getProjectId(), payload.getStatus()));
    }

}
