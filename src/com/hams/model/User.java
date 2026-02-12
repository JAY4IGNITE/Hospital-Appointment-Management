package com.hams.model;

public class User {

    protected String email;
    protected String password;
    protected String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return email; // Using email as username for display
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean validatePassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public String getPassword() {
        return password;
    }
}
