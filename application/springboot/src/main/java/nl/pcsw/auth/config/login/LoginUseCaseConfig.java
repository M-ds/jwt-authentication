package nl.pcsw.auth.config.login;

import nl.pcsw.auth.login.LoginUseCase;
import nl.pcsw.auth.login.impl.LoginUseCaseImpl;
import nl.pcsw.auth.login.infra.PersonRepository;
import nl.pcsw.auth.login.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class LoginUseCaseConfig {

    @Bean
    public LoginUseCase initLoginUseCase(
            PersonRepository personRepository,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        return new LoginUseCaseImpl(personRepository, authenticationManager, jwtUtil);
    }
}
