package com.excalibur.myBlog.form;

import com.excalibur.myBlog.dao.VerificationData;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VerificationForm {

    @Size(max = 20, min = 5)
    private String userLogin;

    @Size(max = 30, min = 5)
    private String userPassword;

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
}
