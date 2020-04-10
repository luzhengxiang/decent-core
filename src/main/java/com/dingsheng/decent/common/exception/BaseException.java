package com.dingsheng.decent.common.exception;

import lombok.Data;

/**
 * @luzhengxiang
 * @create 2020-04-05 23:25
 **/
@Data
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -1976999417705118829L;

    protected String code;
    protected String detailMessage;

    public BaseException(String code,String message, String detailMessage,  Throwable cause){
        super(message, cause);
        this.code = code;
        this.detailMessage = detailMessage;
    }
    public BaseException( String code,String message) {
        this(code,message, message);
    }

    public BaseException(String code , String message, String detailMessage) {
        this(code,message, detailMessage , null);
    }

//    public static long getSerialversionuid() {
//        return serialVersionUID;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getDetailMessage() {
//        return detailMessage;
//    }
//
//    public void setDetailMessage(String detailMessage) {
//        this.detailMessage = detailMessage;
//    }
}
