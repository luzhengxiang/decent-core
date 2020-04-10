package com.dingsheng.decent.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
@Data
public class SysAdmin implements Serializable {
    private Integer id;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 是否锁定 0 否 1是
     */
    private Integer locking;

    /**
     * 密码
     */
    private String loginPwd;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 权限
     */
    private String permission;

    /**
     * 账号
     */
    private String userName;

    private static final long serialVersionUID = 1L;
}