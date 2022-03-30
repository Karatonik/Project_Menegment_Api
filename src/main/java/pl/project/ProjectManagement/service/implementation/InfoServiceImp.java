package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pl.project.ProjectManagement.service.interfaces.InfoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class InfoServiceImp implements InfoService {
    @Value("${my.photoBanner}")
    private String photoBanner;

    @Override
    public Resource getBanner() throws IOException {
        return new ByteArrayResource(Files.readAllBytes(Path
                .of(ResourceUtils.getFile(photoBanner).getPath())));
    }
}
