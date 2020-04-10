package com.dingsheng.decent.dto.api.user;

import com.dingsheng.decent.common.AbstractRequest;
import com.dingsheng.decent.common.exception.BaseException;
import com.dingsheng.decent.constans.api.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @luzhengxiang
 * @create 2020-04-10 14:40
 **/
@Data
public class SendSmsRequest extends AbstractRequest {

    /**
     * 手机号
     */
    public String phone;

    /**
     * 短信类型  1=重置密码 2=注册 3=修改密码 4=修改二级密码
     */
    public Integer type;


    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(phone) || type == null){
            throw new BaseException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
