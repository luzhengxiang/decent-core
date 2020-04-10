package com.dingsheng.decent.common;

/**
 * @luzhengxiang
 * @create 2020-04-05 22:49
 **/
public abstract class AbstractRequest {
    public abstract void requestCheck();

    @Override
    public String toString(){
        return "AbstractRequest{}";
    }
}
