package nl.pcsw.auth.security.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private final String username;
    private final String password;
    private final boolean active;
    private final List<Role> roles;

    public Person(String username, String password, boolean active, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public static class Builder {
        private String username;
        private String password;
        private boolean active;
        private final List<Role> roles = new ArrayList<>();

        public boolean builderIsNotUses() {
            return username == null || username.trim().isEmpty();
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public void isActive(boolean active) {
            this.active = active;
        }

        public void addRole(Role role) {
            this.roles.add(role);
        }

        public Person build() {
            return new Person(username, password, active, roles);
        }
    }
}
