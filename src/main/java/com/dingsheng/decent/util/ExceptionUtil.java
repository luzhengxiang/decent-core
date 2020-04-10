
package com.dingsheng.decent.util;


import com.dingsheng.decent.common.AbstractResponse;
import com.dingsheng.decent.common.exception.BaseException;
import com.dingsheng.decent.common.exception.BizException;
import com.dingsheng.decent.common.exception.ProcessException;
import com.dingsheng.decent.common.exception.ValidateException;

public class ExceptionUtil {

    /**
     * 将下层抛出的异常转换为resp返回码
     *
     * @param e Exception
     * @return
     */
    public static AbstractResponse handlerException4biz(AbstractResponse response, Exception e) throws Exception {
        Exception ex = null;
        if (!(e instanceof Exception)) {
            return null;
        }
        if (e instanceof ValidateException) {
            response.setCode(((ValidateException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if(e instanceof ProcessException) {
            response.setCode(((ProcessException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if(e instanceof BizException) {
            response.setCode(((BizException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if (e instanceof Exception) {
            throw e; //处理不了，抛出去调用方处理
        }
        return response;
    }


    public static void throwsBaseException(String msg){
        throw new BaseException("500",msg);
    }
}
