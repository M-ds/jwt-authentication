package nl.pcsw.auth.login.response;

import java.util.List;
import java.util.Objects;

public record LoginResponse(String token, String username, List<String> roles, String refreshToken) {

    // Simpel validation of the record params
    public LoginResponse {
        Objects.requireNonNull(token);
        Objects.requireNonNull(username);
        Objects.requireNonNull(refreshToken);
    }
}
