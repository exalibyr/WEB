package com.excalibur.myBlog.utils.fileService;

import java.util.List;

public class StoreResult {

    private boolean success;
    private String key;
    private List<String> errors;

    public StoreResult(boolean success, String key, List<String> errors) {
        this.success = success;
        this.key = key;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
