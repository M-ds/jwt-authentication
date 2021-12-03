package nl.pcsw.auth.person;

import nl.pcsw.auth.login.infra.PersonRepository;
import nl.pcsw.auth.security.domain.Person;
import nl.pcsw.auth.security.domain.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Person loadUserByUsername(String username) {
        String query = """
                SELECT p.username, p.password, p.active, r.name
                FROM person p
                         JOIN person_role pr ON p.id = pr.person_id
                         JOIN role r ON r.id = pr.role_id
                WHERE p.username = ?;
                    """;

        try {
            return jdbcTemplate.queryForObject(
                    query,
                    PERSON_DETAIL_ROW_MAPPER(),
                    username
            );
        } catch (DataAccessException dataAccessException) {
            return null;
        }
    }

    private RowMapper<Person> PERSON_DETAIL_ROW_MAPPER() {
        return new RowMapper<>() {

            final Person.Builder personBuilder = new Person.Builder();

            @Override
            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                do {
                    if (personBuilder.builderIsNotUses()) {
                        personBuilder.setUsername(rs.getString("username"));
                        personBuilder.setPassword(rs.getString("password"));
                        personBuilder.isActive(rs.getBoolean("active"));
                    }
                    personBuilder.addRole(Role.valueOf(rs.getString("name")));
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
