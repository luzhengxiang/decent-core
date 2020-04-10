package com.dingsheng.decent.common.exception;

import com.dingsheng.decent.common.ResponseData;
import com.dingsheng.decent.common.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionCommonHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionCommonHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData<Object> handler(Exception e) {
        if (e instanceof BaseException) {
            BaseException exception = (BaseException) e;
            logger.error(exception.getMessage(),e);
            return new ResponseUtil<>().setErrorMsg(exception.getDetailMessage());
        }else {
            logger.error("【系统异常】:[{}]", e.getMessage(), e);
            //TODO 线上环境提示
            return new ResponseUtil<>().setErrorMsg(e.getMessage());
        }
    }

}
