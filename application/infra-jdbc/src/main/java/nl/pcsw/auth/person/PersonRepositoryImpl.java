package nl.pcsw.auth.person;

import nl.pcsw.auth.login.infra.PersonRepository;
import nl.pcsw.auth.security.domain.Person;
import nl.pcsw.auth.security.domain.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepositoryImpl implements PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Person loadUserByUsername(String username) {
        String query = """
                SELECT id, username, password, role
                FROM person
                WHERE username = ?;
                """;

        try {
            return jdbcTemplate.query(
                    query,
                    PERSON_DETAIL_ROW_MAPPER(),
                    username
            );
        } catch (DataAccessException dataAccessException) {
            return null;
        }
    }

    private ResultSetExtractor<Person> PERSON_DETAIL_ROW_MAPPER() {
        return new ResultSetExtractor<>() {
            Person.Builder personBuilder = null;

            @Override
            public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
                do {
                    personBuilder.addRole(Role.valueOf(rs.getString("name")));
                    if (personBuilder == null) {
                        personBuilder.setUsername(rs.getString("username"));
                        personBuilder.setPassword(rs.getString("password"));
                        personBuilder.isActive(rs.getBoolean("active"));
                    }
                } while (rs.next());

                return personBuilder.build();
            }
        };
    }

    @Override
    public boolean usernameExists(String username) {
        String usernameExistsQuery = """
                SELECT COUNT(1)
                FROM person p
                WHERE p.username = ?;
                """;

        var result = jdbcTemplate.queryForObject(
                usernameExistsQuery,
                Integer.class,
                username
        );

        return result != null && result > 0;
    }
}
