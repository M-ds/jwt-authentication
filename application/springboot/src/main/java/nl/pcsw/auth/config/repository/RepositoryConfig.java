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

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final DataSource dataSource;

    public RepositoryConfig() {
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
