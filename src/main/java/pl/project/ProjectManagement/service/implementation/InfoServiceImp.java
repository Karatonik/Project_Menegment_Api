package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.service.interfaces.InfoService;

@Service
public class InfoServiceImp implements InfoService {
    @Value("${my.hello}")
    private String hello;

    @Override
    public String getInfo() {
        return hello;
    }
}
