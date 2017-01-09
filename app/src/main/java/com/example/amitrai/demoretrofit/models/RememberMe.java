package com.example.amitrai.demoretrofit.models;

/**
 * Created by amitrai on 6/1/17.
 * see more at www.github.com/amitrai98
 */

public class RememberMe {
    private String username;
    private String password;

    public RememberMe(String username,
            String password){
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
