package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.service.interfaces.ProjectService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", maxAge = 7200)
public class ProjectController {

    private final ProjectService service;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.service = projectService;
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Boolean> updateProjectDescription(@RequestBody @NotBlank String email,
                                                            @PathVariable @NotBlank Long projectId,
                                                            @RequestBody @NotBlank String description){
        return ResponseEntity.ok(service.updateProjectDescription(email, projectId, description));
    }


}
