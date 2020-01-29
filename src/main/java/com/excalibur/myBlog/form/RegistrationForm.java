package com.excalibur.myBlog.form;

import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.VerificationData;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm {

    @Size(max = 20, min = 5)
    private String userLogin;

    @Size(max = 30, min = 5)
    private String userPassword;

    @Size(max = 20, min = 1)
    private String userName;

    @Size(max = 30, min = 1)
    private String userSurname;

    @Size(max = 100)
    private String userAbout = null;

    public User getUser(){
        return new User(userName, userSurname, userAbout, userLogin);
    }

    public Password getPassword() {
        return new Password(userPassword);
    }

    public VerificationData getValidationData(){
        return new VerificationData(userLogin, userPassword);
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }
}
