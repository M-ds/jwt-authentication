package nl.pcsw.auth.security.domain;

import java.util.List;
import java.util.UUID;

public record Person(UUID id, String username, String password, boolean active, List<Role> roles) {

    public static class Builder {
        private UUID id;
        private String username;
        private String password;
        private boolean active;
        private List<Role> roles;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder isActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder addRole(Role role) {
            this.roles.add(role);
            return this;
        }

        public Person build() {
            assert id != null;
            assert username != null;
            assert password != null;
            assert roles != null;

            return new Person(id, username, password, active, roles);
        }
    }
}
