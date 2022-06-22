package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.request.Parent.ProjectPayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import javax.validation.Valid;
import java.util.List;
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
    @GetMapping("/{projectId}")
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

    @GetMapping("/canjoin/{projectId}")
    public ResponseEntity<?> getStudentsWhoCanJoin(@PathVariable long projectId) {
        return ResponseEntity.ok(projectService.getStudentsWhoCanJoin(projectId));
    }

    @PutMapping("/invite/{projectId}")
    public ResponseEntity<?> inviteStudentsToProject(@RequestHeader("Authorization") String authorization
            , @RequestBody List<StudentDto> studentDtoList, @PathVariable long projectId) {
        return SmartResponseEntity.fromBoolean(this.projectService
                .inviteStudentsToProject(studentDtoList.stream().map(modelWrapper::getStudentFromDto).toList()
                        ,this.infoService.getEmailFromJwt(authorization), projectId));
    }
    @GetMapping("/available")
    public ResponseEntity<?> getProjectsToJoin(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        Page<Project> projectPage = this.projectService.getProjectsToJoin(this.infoService
                .getEmailFromJwt(authorization), pageable);

        return ResponseEntity.ok(new PageImpl<>(projectPage.stream().map(ProjectDto::new)
                .collect(Collectors.toList()), pageable, projectPage.getTotalElements()));
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@RequestHeader("Authorization") String authorization
            , @RequestBody ProjectDto projectDto){
        System.out.println(projectDto);
        return SmartResponseEntity.fromBoolean(this.projectService.updateProject(this.infoService
                        .getEmailFromJwt(authorization) ,modelWrapper.getProjectFromDto(projectDto)));
    }


}
