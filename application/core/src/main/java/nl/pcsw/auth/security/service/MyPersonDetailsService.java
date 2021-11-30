package nl.pcsw.auth.security.service;

import nl.pcsw.auth.login.infra.PersonRepository;
import nl.pcsw.auth.security.domain.Person;
import nl.pcsw.auth.security.domain.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class is the custom implementation of the UserDetailService.
 * Spring provides a default version, but for this project we create our own.
 */
@Service
public class MyPersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public MyPersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.loadUserByUsername(username);
        if (person == null) throw new UsernameNotFoundException("Could not find person with username: " + username);
        return new PersonDetails(person);
    }
}
