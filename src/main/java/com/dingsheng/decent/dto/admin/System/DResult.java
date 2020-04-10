package com.dingsheng.decent.dto.admin.System;


public class DResult {

    private boolean success;
    private String message;
    private Object data;
    private Object dataEx;
    private String url;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getDataEx() {
        return dataEx;
    }

    public void setDataEx(Object dataEx) {
        this.dataEx = dataEx;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void SetSuccess(String msg, Object data) {
        this.success = true;
        this.message = msg;
        this.data = data;
    }

    public void SetSuccess(String msg) {
        this.success = true;
        this.message = msg;
    }

    public void SetSuccess(Object data) {
        this.success = true;
        this.message = "成功";
        this.data = data;
    }

    public void SetError(String msg) {
        this.success = false;
        this.message = msg;
    }
}