package nl.pcsw.auth.login;

import nl.pcsw.auth.common.reply.JsonError;
import nl.pcsw.auth.common.reply.JsonReplyModel;
import nl.pcsw.auth.login.request.LoginRequest;
import nl.pcsw.auth.login.response.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/user/auth/login")
    public JsonReplyModel<LoginResponse> login(
            @RequestBody final LoginRequest loginRequest
    ) {
        final var username = loginRequest.username();
        final var password = loginRequest.password();

        if (isEmptyInput(username, password)) {
            return new JsonReplyModel<>(
                    false,
                    new JsonError("Username or password is empty. Please provide one"),
                    null
            );
        }

        final var request = new LoginUseCase.Request(username, password);

        try {
            var response = loginUseCase.login(request);
            return new JsonReplyModel<>(
                    true,
                    null,
                    new LoginResponse(
                            response.loginPerson().token(),
                            response.loginPerson().username(),
                            response.loginPerson().roles(),
                            response.loginPerson().refreshToken()
                    )
            );
        } catch (Exception ex) {
            String errorMessage;
            if (ex.getMessage().contains("Bad credentials")) {
                errorMessage = "Invalid password, please try again.";
            } else {
                errorMessage = ex.getMessage();
            }
            return new JsonReplyModel<>(
                    false,
                    new JsonError(errorMessage),
                    null
            );
        }

    }

    private boolean isEmptyInput(String username, String password) {
        return username.trim().isEmpty() && password.trim().isEmpty();
    }
}
