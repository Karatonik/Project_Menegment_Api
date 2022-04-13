package pl.project.ProjectManagement.model.enums;

import lombok.ToString;

public enum MailRole {
    UpdateEmail("email"),
    UpdateRole("role"),
    DeletePerson("delete"),
    UpdatePassword("password");

    private final String url;

    MailRole(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
