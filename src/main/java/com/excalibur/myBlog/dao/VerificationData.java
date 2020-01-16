package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_verification_data", schema = "public")
public class VerificationData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_verification_data_id_seq")
    @SequenceGenerator(name = "user_verification_data_id_seq", sequenceName = "user_verification_data_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "user_login", unique = true, length = 20, nullable = false, updatable = false)
    private String login;

    @Column(name = "user_password", nullable = false, length = 30)
    private String password;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private User user;

    public VerificationData() {
    }

    public VerificationData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
