package pl.project.ProjectManagement.service.interfaces;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface InfoService {

    Resource getBanner() throws IOException;
}
