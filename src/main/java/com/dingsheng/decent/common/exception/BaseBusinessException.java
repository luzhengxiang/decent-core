package com.dingsheng.decent.common.exception;

import lombok.Data;

@Data
public class BaseBusinessException extends RuntimeException{

    protected String          errorCode;

    protected String          message;

    protected String        extFields;

    public BaseBusinessException() {
        super();
    }

    public BaseBusinessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BaseBusinessException(Throwable arg0) {
        super(arg0);
    }

    public BaseBusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseBusinessException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseBusinessException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseBusinessException(String errorCode, String message,String extFields, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
        this.extFields=extFields;
    }
}
