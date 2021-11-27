package com.example.android.friendlyapp;

public class User {

    private String name, phoneNumber,uid;

    public User() {

    }

    public User(String name, String phonenumber, String uid) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phonenumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}