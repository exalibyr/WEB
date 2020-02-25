package com.excalibur.myBlog.dao.response;

public class ResponseBody<T> {

    private boolean success;
    private String message;
    private T entity;

    public ResponseBody(boolean success, String message, T entity) {
        this.success = success;
        this.message = message;
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
