package com.dingsheng.decent.dto.api;

import com.dingsheng.decent.common.AbstractRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @luzhengxiang
 * @create 2020-04-06 14:20
 **/
@Data
public class UserLoginRequest extends AbstractRequest {
    private String userName;
    private String password;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
//            throw new BaseException(
//                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
//                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}

