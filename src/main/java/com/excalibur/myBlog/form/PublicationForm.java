package com.excalibur.myBlog.form;

import com.excalibur.myBlog.dao.Publication;

import javax.validation.constraints.Size;

public class PublicationForm {

    private String username;

    private Integer publicationId;

    @Size(max = 50, min = 1)
    private String title;

    @Size(max = 500, min = 1)
    private String content;

    private String base64image;

    public PublicationForm() {
    }

    public PublicationForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PublicationForm(@Size(max = 50, min = 1) String title, @Size(max = 500, min = 1) String content, String base64image) {
        this.title = title;
        this.content = content;
        this.base64image = base64image;
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
        Publication publication = new Publication(title, content);
        if (this.publicationId != null) publication.setId(this.publicationId);
        return publication;
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

    public String getBase64image() {
        return base64image;
    }

    public void setBase64image(String base64image) {
        this.base64image = base64image;
    }
}
