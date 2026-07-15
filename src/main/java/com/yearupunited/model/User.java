package com.yearupunited.model;

public class User {
    private final String userId;
    private String userName;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {return this.userId;}
    public String getUserName() {return this.userName;}

}
