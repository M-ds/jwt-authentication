package nl.pcsw.auth.login;

import nl.pcsw.auth.login.domain.LoginPerson;

public interface LoginUseCase {

    Response login(Request request);
    record Request(String username, String password) {}
    record Response(LoginPerson loginPerson, boolean valid, String errorMessage) {}

}
