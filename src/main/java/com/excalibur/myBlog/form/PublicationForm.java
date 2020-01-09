package com.excalibur.myBlog.form;

import com.excalibur.myBlog.dao.Publication;

import javax.validation.constraints.Size;

public class PublicationForm {

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
