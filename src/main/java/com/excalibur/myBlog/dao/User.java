package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
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

    @OneToMany(mappedBy = "user")
    @Cascade(value = CascadeType.DELETE)
    private List<Publication> publications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.DELETE)
    private List<Password> passwords;

    @Column(name = "avatar", unique = true)
    private String avatar;

    @OneToMany(mappedBy = "user")
    @Cascade(value = CascadeType.DELETE)
    private Set<File> files;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.ALL)
    @JoinTable(name = "user_role", schema = "public",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)})
    private Set<Role> roles;

    @Column(name = "username", nullable = false, unique = true, updatable = false, length = 20)
    private String username;


    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(String name, String surname, String about, String username) {
        this.name = name;
        this.surname = surname;
        this.about = about;
        this.username = username;
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

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }

    public Password getCurrentPassword() {
        return getPasswords().get(0);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
