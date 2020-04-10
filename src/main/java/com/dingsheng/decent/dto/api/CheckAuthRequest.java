package com.dingsheng.decent.dto.api;

import com.dingsheng.decent.common.AbstractRequest;
import com.dingsheng.decent.common.exception.BaseException;
import com.dingsheng.decent.constans.api.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @luzhengxiang
 * @create 2020-04-06 00:52
 **/
@Data
public class CheckAuthRequest extends AbstractRequest {
    private String token;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(token)){
            throw new BaseException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
