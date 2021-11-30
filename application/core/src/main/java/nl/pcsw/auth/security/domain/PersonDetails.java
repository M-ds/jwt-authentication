package nl.pcsw.auth.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person
                .roles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return person.password();
    }

    @Override
    public String getUsername() {
        return person.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return person.active();
    }

    @Override
    public boolean isAccountNonLocked() {
        return person.active();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return person.active();
    }

    @Override
    public boolean isEnabled() {
        return person.active();
    }
}
