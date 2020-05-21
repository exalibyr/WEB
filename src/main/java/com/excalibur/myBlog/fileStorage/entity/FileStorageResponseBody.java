package com.excalibur.myBlog.fileStorage.entity;

import com.excalibur.myBlog.dao.response.ResponseBody;

public class FileStorageResponseBody extends ResponseBody {

    private String key;
    private String fileName;

    public FileStorageResponseBody() {
    }

    public FileStorageResponseBody(boolean success, String message) {
        super(success, message);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
