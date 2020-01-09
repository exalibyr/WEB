package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_data", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_id_seq")
    @SequenceGenerator(name = "user_data_id_seq", sequenceName = "user_data_id_seq", allocationSize = 1)
    private Integer id;

    @Size(max = 20, min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 30, min = 1)
    @Column(name = "surname", nullable = false)
    private String surname;

    @Size(max = 100)
    @Column(name = "about")
    private String about;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<Publication> publications;

    @OneToOne(optional = false, mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.ALL)
    private VerificationData verificationData;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "has_avatar")
    private boolean hasAvatar;


    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(String name, String surname, String about) {
        this.name = name;
        this.surname = surname;
        this.about = about;
    }

    public void addPublication(Publication publication){
        if(publications == null){
            publications = new ArrayList<>();
        }
        publications.add(publication);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public VerificationData getVerificationData() {
        return verificationData;
    }

    public void setVerificationData(VerificationData verificationData) {
        this.verificationData = verificationData;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean hasAvatar() {
        return hasAvatar;
    }

    public void setHasAvatar(boolean hasAvatar) {
        this.hasAvatar = hasAvatar;
    }
}
