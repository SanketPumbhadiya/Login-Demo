package com.example.demo_login.model;

public class ModelApiRequestLogin {
    String username,password;
    public ModelApiRequestLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
