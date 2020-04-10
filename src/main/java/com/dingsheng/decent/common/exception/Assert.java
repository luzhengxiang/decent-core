package com.dingsheng.decent.common.exception;

import com.dingsheng.decent.constans.api.SysRetCodeConstants;

/**
 * @luzhengxiang
 * @create 2020-04-09 16:44
 **/
public class Assert {

    public static void isNull(Object o) {
        if (o == null)
            throw new BaseException(
                    SysRetCodeConstants.REQUISITE_PARAMETER_NOT_EXIST.getCode(),
                    SysRetCodeConstants.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
    }


    public static void isTrue(boolean o) {
        if (o)
            throw new BaseException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
    }
}
