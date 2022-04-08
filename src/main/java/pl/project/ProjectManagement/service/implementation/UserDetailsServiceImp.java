package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.ProjectManagement.builder.PersonBuilder;
import pl.project.ProjectManagement.model.Person;
import pl.project.ProjectManagement.repository.PersonRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public UserDetailsServiceImp(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("Person Not Found with email: " + email));
        return PersonBuilder.build(person);
    }
}
