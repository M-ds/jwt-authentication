package nl.pcsw.auth.login.impl;

import nl.pcsw.auth.login.LoginUseCase;
import nl.pcsw.auth.login.domain.LoginPerson;
import nl.pcsw.auth.login.infra.PersonRepository;
import nl.pcsw.auth.login.util.JwtUtil;
import nl.pcsw.auth.security.domain.PersonDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;
import java.util.stream.Collectors;

public class LoginUseCaseImpl implements LoginUseCase {

    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginUseCaseImpl(PersonRepository personRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.personRepository = personRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Response login(Request request) {
        final var username = request.username();
        final var password = request.password();

        var exists = personRepository.usernameExists(username);
        if (!exists) {
            return new Response(null, false, "username does not exits!");
        }

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        var jwtToken = jwtUtil.generateToken(authentication);
        var personDetails = (PersonDetails) authentication.getPrincipal();
        var roles = personDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginPerson loginPerson = new LoginPerson.Builder()
                .setToken(jwtToken)
                .setUsername(personDetails.getUsername())
                .setRoles(roles)
                .setRefreshToken(UUID.randomUUID().toString())
                .build();

        return new Response(
                loginPerson,
                true,
                ""
        );
    }
}
