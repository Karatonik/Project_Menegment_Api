package pl.project.ProjectManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.hash.Hashing;
import lombok.*;
import pl.project.ProjectManagement.model.enums.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "person")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {

    @Id
    @Column(length = 50)
    private String email;

    @JsonIgnore
    private String password;

    private String token;

    @Column(nullable = false, columnDefinition = "integer default 0") // role = User
    private Role role;


    @OneToMany(mappedBy = "projectOwner")
    @ToString.Exclude
    private List<Project> ownedProjects;

    public Person(String email, String password) {
        this.ownedProjects = new ArrayList<>();
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        setToken();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), new Date(System.currentTimeMillis()));
    }


    public void setToken() {
        this.token = Hashing.sha256()
                .hashString(String.valueOf(hashCode()), StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(getEmail(), person.getEmail()) && Objects.equals(getPassword(),
                person.getPassword()) && Objects.equals(getToken(), person.getToken()) &&
                getRole() == person.getRole();
    }
}
