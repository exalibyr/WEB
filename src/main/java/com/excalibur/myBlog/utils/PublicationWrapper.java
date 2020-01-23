package com.excalibur.myBlog.utils;

import com.excalibur.myBlog.dao.Publication;

public class PublicationWrapper {

    private Publication publication;

    private String localDateTimeString;

    public PublicationWrapper(Publication publication) {
        this.publication = publication;
        this.localDateTimeString = ZonedDateTimeFormatter.getLocalDateTimeString(publication.getDateTime());
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getLocalDateTimeString() {
        return localDateTimeString;
    }

    public void setLocalDateTimeString(String localDateTimeString) {
        this.localDateTimeString = localDateTimeString;
    }
}
