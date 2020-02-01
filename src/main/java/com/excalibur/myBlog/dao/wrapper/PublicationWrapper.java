package com.excalibur.myBlog.dao.wrapper;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.utils.ApplicationUtils;

public class PublicationWrapper {

    private Publication publication;

    private String localDateTimeString;

    public PublicationWrapper(Publication publication) {
        this.publication = publication;
        this.localDateTimeString = ApplicationUtils.getLocalDateTimeString(publication.getDateTime());
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
