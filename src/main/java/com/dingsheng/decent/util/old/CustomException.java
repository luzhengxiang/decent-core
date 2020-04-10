package com.dingsheng.decent.util.old;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {
    public CustomException() {}

    public CustomException(String message) {
        super(message);
    }
}
