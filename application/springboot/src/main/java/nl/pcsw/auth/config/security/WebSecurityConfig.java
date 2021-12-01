package nl.pcsw.auth.config.security;

import nl.pcsw.auth.security.service.MyPersonDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final MyPersonDetailsService myPersonDetailsService;

    public WebSecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, JwtRequestFilter jwtRequestFilter, MyPersonDetailsService myPersonDetailsService) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.myPersonDetailsService = myPersonDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
