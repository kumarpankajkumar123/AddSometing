package com.example.notesapp.UserData;

public class CurrentDetails {
    String Username,email,password,confirmPassword,userId;

    public CurrentDetails() {
    }

    public CurrentDetails(String username, String email, String password, String confirmPassword) {
        Username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public CurrentDetails(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
