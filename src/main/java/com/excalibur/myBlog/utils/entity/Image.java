package com.excalibur.myBlog.utils.entity;

import org.springframework.http.MediaType;
import org.springframework.web.server.MediaTypeNotSupportedStatusException;

public class Image extends Media {

    public Image(String contentType, byte[] content) {
        super();
        switch (contentType) {
            case MediaType.IMAGE_JPEG_VALUE: {
                this.setMediaType(MediaType.IMAGE_JPEG);
                this.setContent(content);
                break;
            }
            case MediaType.IMAGE_PNG_VALUE: {
                this.setMediaType(MediaType.IMAGE_PNG);
                this.setContent(content);
                break;
            }
            default: {
                throw new MediaTypeNotSupportedStatusException(contentType + " is not supported");
            }
        }

    }


}
