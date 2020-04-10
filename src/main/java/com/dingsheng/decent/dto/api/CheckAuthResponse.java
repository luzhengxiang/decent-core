package com.dingsheng.decent.dto.api;

import com.dingsheng.decent.common.AbstractResponse;
import lombok.Data;

/**
 * @luzhengxiang
 * @create 2020-04-06 13:29
 **/
@Data
public class CheckAuthResponse extends AbstractResponse {

    public String userInfo;
}
