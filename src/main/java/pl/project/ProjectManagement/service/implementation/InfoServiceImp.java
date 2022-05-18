package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.jwt.JwtUtils;
import pl.project.ProjectManagement.service.interfaces.InfoService;

@Service
public class InfoServiceImp implements InfoService {
    @Value("${my.hello}")
    private String hello;


    private final JwtUtils jwtUtils;

    @Autowired
    public InfoServiceImp(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String getInfo() {
        return hello;
    }

    @Override
    public String getEmailFromJwt(String authorization) {
        return jwtUtils.getUserNameFromJwtToken(authorization.substring(7));
    }
}
