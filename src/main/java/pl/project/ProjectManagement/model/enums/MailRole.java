package pl.project.ProjectManagement.model.enums;

import lombok.ToString;

@ToString
public enum MailRole {
    email("email"),
    role("role"),
    delete("delete"),
    password("password");

    private final String url;

    MailRole(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
