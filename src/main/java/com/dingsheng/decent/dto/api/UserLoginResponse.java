package com.dingsheng.decent.dto.api;

import com.dingsheng.decent.common.AbstractResponse;
import lombok.Data;

@Data
public class UserLoginResponse extends AbstractResponse {

    private Long id;
    private String userName;
    private String token;
    private String telphone;
}
