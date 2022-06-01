package pl.project.ProjectManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ProjectManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementApplication.class, args);
    }

    @EventListener
    public static void initAdmin(){

    }

}
