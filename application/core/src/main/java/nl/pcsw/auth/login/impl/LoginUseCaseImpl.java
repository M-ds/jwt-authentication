package nl.pcsw.auth.login.impl;

import nl.pcsw.auth.login.LoginUseCase;
import nl.pcsw.auth.login.infra.PersonRepository;

public class LoginUseCaseImpl implements LoginUseCase {

    private final PersonRepository personRepository;

    public LoginUseCaseImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Response login(Request request) {
        final var username = request.username();
        final var password = request.password();

        var exists = personRepository.usernameExists(username);
        if (!exists) {
            return new Response(null, false, "username does not exits!");
        }


        return null;
    }
}
