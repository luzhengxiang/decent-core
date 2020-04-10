package com.dingsheng.decent.dto.api.user;

import com.dingsheng.decent.common.AbstractRequest;
import com.dingsheng.decent.common.exception.BaseException;
import com.dingsheng.decent.constans.api.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class RegisterRequest extends AbstractRequest {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 推荐码
     */
    private String referralCode;

    /**
     * 登陆密码
     */
    private String loginPwd;
    /**
     * 支付密码
     */
    private String payPwd;


    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)|| StringUtils.isBlank(referralCode)
                || StringUtils.isBlank(loginPwd)|| StringUtils.isBlank(payPwd)){
            throw new BaseException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
