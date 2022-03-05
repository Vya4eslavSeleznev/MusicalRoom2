package com.example.room.model;

public class Token {

    private int id;
    private String userName;
    private String token;
    private String role;

    public Token(int id, String userName, String token, String role) {
        this.id = id;
        this.userName = userName;
        this.token = token;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
