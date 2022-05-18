package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.request.UpdateStudyTypePayload;
import pl.project.ProjectManagement.model.response.SmartResponseEntity;
import pl.project.ProjectManagement.service.interfaces.InfoService;
import pl.project.ProjectManagement.service.interfaces.ModelWrapper;
import pl.project.ProjectManagement.service.interfaces.StudentService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*", maxAge = 7200)
public class StudentController {

    private final StudentService studentService;
    private final ModelWrapper modelWrapper;

    private final InfoService infoService;

    @Autowired
    public StudentController(StudentService studentService, ModelWrapper modelWrapper, InfoService infoService) {
        this.studentService = studentService;
        this.modelWrapper = modelWrapper;
        this.infoService = infoService;
    }

    @PostMapping
    public ResponseEntity<?> setStudent(@RequestBody StudentDto studentDto) {
        studentDto = new StudentDto(this.studentService.
                setStudent(this.modelWrapper.getStudentFromDto(studentDto)));

        if (studentDto.equals(new StudentDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping
    public ResponseEntity<?> getStudent(@RequestHeader("Authorization") String authorization) {
        StudentDto studentDto = new StudentDto(this.studentService.getStudent(this.infoService
                .getEmailFromJwt(authorization)));

        if (studentDto.equals(new StudentDto())) {
            return SmartResponseEntity.getNotAcceptable();
        }
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String authorization,
                                            @PathVariable String token,
                                            Pageable pageable, long size) {

        return ResponseEntity.ok(new PageImpl<>(this.studentService.
                getAllStudents(this.infoService.getEmailFromJwt(authorization), token)
                .stream().map(StudentDto::new).collect(Collectors.toList()), pageable, size));
    }

    @PutMapping("/type")
    public ResponseEntity<?> updateStudentType(@RequestHeader("Authorization") String authorization,
                                               @RequestBody @Valid UpdateStudyTypePayload payload) {

        return SmartResponseEntity.fromBoolean(this.studentService
                .updateStudentType(this.infoService
                        .getEmailFromJwt(authorization), payload.getStudyType()));
    }

    @PutMapping("/join/{projectId}")
    public ResponseEntity<?> joinToProject(@RequestHeader("Authorization") String authorization,
                                           @PathVariable long projectId) {

        return SmartResponseEntity.fromBoolean(this.studentService
                .joinToProject(this.infoService.getEmailFromJwt(authorization), projectId));
    }
}
