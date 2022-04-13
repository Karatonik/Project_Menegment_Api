package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.enums.StudyType;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*", maxAge = 7200)
public class StudentController {

    private final StudentService studentService;
    private final ModelWrapper modelWrapper;

    @Autowired
    public StudentController(StudentService studentService, ModelWrapper modelWrapper) {
        this.studentService = studentService;
        this.modelWrapper = modelWrapper;
    }

    @PostMapping
    public ResponseEntity<StudentDto> setStudent(@RequestBody @NotBlank StudentDto studentDto) {

        return ResponseEntity.ok(new StudentDto(this.studentService.
                setStudent(this.modelWrapper.getStudentFromDto(studentDto))));
    }

    @GetMapping("/{email}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable @NotBlank String email) {

        return ResponseEntity.ok(new StudentDto(this.studentService.getStudent(email)));
    }

    @GetMapping("/all/{adminEmail}")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable @NotBlank String adminEmail,
                                                           @RequestBody @NotBlank String token) {

        return ResponseEntity.ok(this.studentService.
                getAllStudents(adminEmail, token).stream().map(StudentDto::new).collect(Collectors.toList()));
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateStudentType(@PathVariable @NotBlank String email,
                                               @RequestBody @NotBlank StudyType studyType) {

        return SmartResponseEntity.fromBoolean(this.studentService.updateStudentType(email, studyType));
    }

    @PutMapping("/join/{email}")
    public ResponseEntity<?> joinToProject(@PathVariable @NotBlank String email,
                                           @RequestBody @NotBlank Long projectId) {

        return SmartResponseEntity.fromBoolean(this.studentService.joinToProject(email, projectId));
    }
}
