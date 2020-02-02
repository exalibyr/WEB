package com.excalibur.myBlog.dao.wrapper;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.utils.ApplicationUtils;

public class UserWrapper {

    private User user;

    private String avatarURI;

    public UserWrapper(User user) {
        this.user = user;
        this.avatarURI = ApplicationUtils.getUserAvatarURI(this.user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvatarURI() {
        return avatarURI;
    }

    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
    }
}
