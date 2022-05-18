package pl.project.ProjectManagement.service.interfaces;

public interface InfoService {

    String getInfo();

    String getEmailFromJwt(String authorization);
}
