package com.dingsheng.decent.common;

/**
 * @luzhengxiang
 * @create 2020-04-05 22:25
 **/
public class ResponseUtil<T> {

    private ResponseData<T> responseData;

    public ResponseUtil() {
        responseData = new ResponseData<>();
        responseData.setSuccess(true);
        responseData.setMessage("success");
        responseData.setCode("200");
    }

    public ResponseData<T> setData(T t) {
        this.responseData.setResult(t);
        this.responseData.setSuccess(true);
        responseData.setCode("200");
        return this.responseData;
    }

    public ResponseData<T> success() {
        this.responseData.setResult(null);
        this.responseData.setSuccess(true);
        responseData.setCode("200");
        return this.responseData;
    }

    public ResponseData<T> setData(T t, String msg) {
        this.responseData.setResult(t);
        this.responseData.setSuccess(true);
        this.responseData.setMessage(msg);
        responseData.setCode("200");
        return this.responseData;
    }

    public ResponseData<T> setErrorMsg(String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode("500");
        return this.responseData;
    }

    public ResponseData<T> setErrorMsg(String code, String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode("500");
        return this.responseData;
    }
}
