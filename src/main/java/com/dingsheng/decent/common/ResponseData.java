package com.dingsheng.decent.common;

import lombok.Data;

/**
 * @luzhengxiang
 * @create 2020-04-05 22:26
 **/
@Data
public class ResponseData<T> {

    private boolean success;

    private String message; //消息

    private String code;

    private T result; //返回的数据


}
