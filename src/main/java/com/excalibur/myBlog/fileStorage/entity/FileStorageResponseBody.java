package com.excalibur.myBlog.fileStorage.entity;

import java.util.List;

public class FileStorageResponseBody {

    private String key;
    private String fileName;
    private Boolean success;
    private List<String> errors;

    public FileStorageResponseBody() {
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
