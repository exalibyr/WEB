package com.excalibur.myBlog.dao.wrapper;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

public class PublicationWrapper {

    private Publication publication;

    private String localDateTimeString;

    private String encryptedID;

    private List<String> mediaSources = new ArrayList<>();

    public PublicationWrapper(Publication publication) {
        this.publication = publication;
        this.encryptedID = ApplicationUtils.getEncryptedID(this.publication.getId());
        this.localDateTimeString = ApplicationUtils.getLocalDateTimeString(publication.getDateTime());
        String userFilesURI = ApplicationUtils.getUserFilesURI(publication.getUser().getId());
        for (File file : publication.getFiles()) {
            mediaSources.add(userFilesURI + file.getName());
        }
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

    public String getEncryptedID() {
        return encryptedID;
    }

    public void setEncryptedID(String encryptedID) {
        this.encryptedID = encryptedID;
    }

    public List<String> getMediaSources() {
        return mediaSources;
    }

    public void setMediaSources(List<String> mediaSources) {
        this.mediaSources = mediaSources;
    }
}
