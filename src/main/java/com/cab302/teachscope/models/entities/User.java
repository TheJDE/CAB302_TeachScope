package com.cab302.teachscope.models.entities;

public class Users {
    //Fields
    private String userName;
    private String passWord;

    //Constructors
    public Users(String passWord){
        this.passWord = passWord;
    }

    //Getters and Setters
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
