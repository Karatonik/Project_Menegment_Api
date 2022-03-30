package pl.project.ProjectManagement.builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.model.enums.Role;

import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PersonBuilder implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;

    @JsonIgnore
    private String password;

    private Role role;

    private Collection<? extends GrantedAuthority> authorities;

    public PersonBuilder(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static PersonBuilder build(Person person) {

        return new PersonBuilder(
                person.getEmail(),
                person.getPassword(),person.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonBuilder personBuilder = (PersonBuilder) o;
        return Objects.equals(email, personBuilder.email);
    }
}
