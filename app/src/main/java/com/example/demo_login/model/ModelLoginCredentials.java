package com.example.demo_login.model;

public class ModelLoginCredentials {
    String userName, password;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCredentialsAvailable(){
        return (!userName.isEmpty() && !password.isEmpty());
    }
}
