package com.excalibur.myBlog.form;

import javax.validation.constraints.Size;

public class EditProfileForm {

    @Size(max = 20, min = 1)
    private String name;

    @Size(max = 30, min = 1)
    private String surname;

    @Size(max = 100)
    private String about;

    private byte[] avatar;

    public EditProfileForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
