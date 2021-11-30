package nl.pcsw.auth.config.login;

import nl.pcsw.auth.login.LoginUseCase;
import nl.pcsw.auth.login.impl.LoginUseCaseImpl;
import nl.pcsw.auth.login.infra.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginUseCaseConfig {

    @Bean
    public LoginUseCase initLoginUseCase(
        PersonRepository personRepository
    ) {
        return new LoginUseCaseImpl(personRepository);
    }
}
