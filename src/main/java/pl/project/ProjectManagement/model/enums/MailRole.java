package pl.project.ProjectManagement.model.enums;

import lombok.ToString;
@ToString
public enum MailRole {
    updateEmail("email"),
    updateRole("role"),
    deletePerson("delete"),
    updatePassword("password");

    private final String url;

    MailRole(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
