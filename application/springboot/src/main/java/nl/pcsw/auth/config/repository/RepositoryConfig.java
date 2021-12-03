package nl.pcsw.auth.config.repository;

import nl.pcsw.auth.login.infra.PersonRepository;
import nl.pcsw.auth.person.PersonRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {

    private final String url;
    private final String username;
    private final String password;
    private final DataSource dataSource;

    public RepositoryConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.dataSource = createDataSource();
    }

    @Bean
    public PersonRepository initPersonRepository() {
        return new PersonRepositoryImpl(dataSource);
    }

    private DataSource createDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
