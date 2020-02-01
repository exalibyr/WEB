package com.excalibur.myBlog.form;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;

import javax.validation.constraints.Size;

public class PublicationForm {

    private String username;

    private Integer publicationId;

    @Size(max = 50, min = 1)
    private String title;

    @Size(max = 500, min = 1)
    private String content;

    public PublicationForm() {
    }

    public PublicationForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Integer publicationId) {
        this.publicationId = publicationId;
    }

    public Publication getPublication(){
        return new Publication(title, content);
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
}
