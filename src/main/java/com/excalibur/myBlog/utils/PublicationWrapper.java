package com.excalibur.myBlog.utils;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.User;

import java.time.ZonedDateTime;
import java.util.Set;

public class PublicationWrapper {

    private Publication publication;

    private String localDateTime;

    public PublicationWrapper(Publication publication, String localDateTime) {
        this.publication = publication;
        this.localDateTime = localDateTime;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }
}
