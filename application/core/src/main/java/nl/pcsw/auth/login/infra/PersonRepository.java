package nl.pcsw.auth.login.infra;

import nl.pcsw.auth.security.domain.Person;

public interface PersonRepository {

    Person loadUserByUsername(String username);

    boolean usernameExists(String username);
}
