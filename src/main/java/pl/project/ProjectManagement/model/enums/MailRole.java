package pl.project.ProjectManagement.model.enums;

import lombok.ToString;

@ToString
public enum MailRole {
    UpdateEmail("email"),
    UpdateRole("role"),
    DeletePerson("delete"),
    UpdatePassword("password"),
    None("");

    private final String url;

    MailRole(String url) {
        this.url = url;
    }
}
