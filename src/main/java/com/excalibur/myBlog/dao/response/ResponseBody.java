package com.excalibur.myBlog.dao.response;

public class ResponseBody {

    private boolean success;
    private String message;

    public ResponseBody() {
    }

    public ResponseBody(boolean success, String message) {
        this.success = success;
        this.message = message;
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

}
