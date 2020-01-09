package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "user_publication", schema = "public")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_publication_id_seq")
    @SequenceGenerator(name = "user_publication_id_seq", sequenceName = "user_publication_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "picture_url")
    private String pictureUrl;

    @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.ALL)
    private Set<File> files;

    public Publication() {
    }

    public Publication(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }
}
