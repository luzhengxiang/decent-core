package com.dingsheng.decent.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 会员表
 */
@Data
@Table(name = "u_user_member")
public class Member implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 婚姻状态-0未填写，1未婚，2已婚，3离异，4丧偶
     */
    private Integer maritalStatus;

    /**
     * 居住地：省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县街道
     */
    private String district;

    /**
     * 学历
     */
    private String educate;

    /**
     * 行业
     */
    private String industry;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 收入
     */
    private String earning;

    /**
     * 登录密码
     */
    private String pwd;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 提款密码
     */
    private String drawpwd;

    /**
     * 实名认证
     */
    private Byte validated;

    private Date regdate;

    /**
     * 推荐码
     */
    private String trackid;

    /**
     * 最后登录时间
     */
    private Date logintime;

    private String utmContent;

    /**
     * 用户状态(0:正常,2:锁定提款)
     */
    private Integer status;

    /**
     * 用户头像
     */
    private String headerimg;

    /**
     * 0：VIP,1：A类，2：B类，3：C类，
     */
    private Integer level;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 注册来源（0:其它,1:PC,2:H5,3:android,4:IOS)
     */
    private Integer regSource;

    /**
     * 终端ID
     */
    private String terminalId;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 锁定状态（0否，1是）
     */
    private int locking;

    /**
     * 锁定时间
     */
    private Date lockTime;

    /**
     * 上级用户的推荐码
     */
    private String referralCode;


    private static final long serialVersionUID = 1L;
}