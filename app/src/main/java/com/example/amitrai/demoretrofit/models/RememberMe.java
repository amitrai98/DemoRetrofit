package com.example.amitrai.demoretrofit.models;

/**
 * Created by amitrai on 6/1/17.
 * see more at www.github.com/amitrai98
 */

public class RememberMe {
    String username;
    String password;

    public RememberMe(String username,
            String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
