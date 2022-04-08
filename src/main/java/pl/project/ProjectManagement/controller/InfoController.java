package pl.project.ProjectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.project.ProjectManagement.service.interfaces.InfoService;


@RestController
@CrossOrigin(origins = "*", maxAge = 7200)
public class InfoController {
    private final InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok(infoService.getInfo());
    }

    @GetMapping("/1")
    public String getOne() {
        return "One";
    }
}
