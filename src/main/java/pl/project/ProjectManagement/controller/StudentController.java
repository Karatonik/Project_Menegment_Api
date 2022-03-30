package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.project.ProjectManagement.service.interfaces.StudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*", maxAge = 7200)
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService studentService) {
        this.service = studentService;
    }
}
