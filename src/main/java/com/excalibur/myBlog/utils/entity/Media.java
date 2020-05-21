package com.excalibur.myBlog.utils.entity;

import org.springframework.http.MediaType;

public abstract class Media {

    private MediaType mediaType;
    private byte[] content;

    public Media() {
    }

    public Media(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Media(MediaType mediaType, byte[] content) {
        this.mediaType = mediaType;
        this.content = content;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
