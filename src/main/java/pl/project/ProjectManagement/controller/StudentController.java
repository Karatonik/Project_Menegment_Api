package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.request.Parent.AdminAccessPayload;
import pl.project.ProjectManagement.model.request.Parent.EmailPayload;
import pl.project.ProjectManagement.model.request.SecoundParent.WithProjectPayload;
import pl.project.ProjectManagement.model.request.UpdateStudyTypePayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    public ResponseEntity<?> setStudent(@RequestBody  StudentDto studentDto) {
        studentDto = new StudentDto(this.studentService.
                setStudent(this.modelWrapper.getStudentFromDto(studentDto)));

        if (studentDto.equals(new StudentDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping
    public ResponseEntity<?> getStudent(@RequestBody @Valid EmailPayload payload) {
        StudentDto studentDto = new StudentDto(this.studentService.getStudent(payload.getEmail()));

        if (studentDto.equals(new StudentDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents(@RequestBody @Valid AdminAccessPayload payload,
                                            Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.studentService.
                getAllStudents(payload.getAdminEmail(), payload.getToken())
                .stream().map(StudentDto::new).collect(Collectors.toList()), pageable, size));
    }

    @PutMapping("/type")
    public ResponseEntity<?> updateStudentType(@RequestBody @Valid UpdateStudyTypePayload payload) {

        return SmartResponseEntity.fromBoolean(this.studentService
                .updateStudentType(payload.getEmail(), payload.getStudyType()));
    }

    @PutMapping("/join")
    public ResponseEntity<?> joinToProject(@RequestBody @Valid WithProjectPayload payload) {

        return SmartResponseEntity.fromBoolean(this.studentService
                .joinToProject(payload.getEmail(), payload.getProjectId()));
    }
}
