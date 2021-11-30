package nl.pcsw.auth.login.domain;

import java.util.List;

public record LoginPerson(String token, String username, List<String> roles, String refreshToken) {

    public static class Builder {
        private String token;
        private String username;
        private List<String> roles;
        private String refreshToken;

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public LoginPerson build() {
            return new LoginPerson(token, username, roles, refreshToken);
        }
    }
}
