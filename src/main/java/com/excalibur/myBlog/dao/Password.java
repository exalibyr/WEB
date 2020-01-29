package com.excalibur.myBlog.dao;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "password", schema = "public")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_id_seq")
    @SequenceGenerator(name = "password_id_seq", sequenceName = "password_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "value", length = 30, updatable = false, nullable = false)
    private String value;

    @Column(name = "created_date_time", updatable = false, nullable = false)
    private ZonedDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public Password() {
    }

    public Password(String password) {
        this.value = password;
        this.createdDateTime = ZonedDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
