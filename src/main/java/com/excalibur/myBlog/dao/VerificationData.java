package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_verification_data", schema = "public")
public class VerificationData {

    @Id
    private Integer userId;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_password", nullable = false)
    private String password;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @JoinTable(name = "user_role", schema = "public",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)})
    private Set<Role> roles;

    public VerificationData() {
    }

    public VerificationData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
