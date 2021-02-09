package com.example.easyeat.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String usernname;
    private String email;
    private String password;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsernname() {
        return usernname;
    }

    public void setUsernname(String usernname) {
        this.usernname = usernname;
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
}