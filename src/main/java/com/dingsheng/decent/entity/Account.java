package com.dingsheng.decent.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 
 * 用户帐户信息表
 */
@Data
@Table(name = "u_user_account")
public class Account implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 总资产
     */
    private BigDecimal totalAssets;

    /**
     * 冻结资金
     */
    private BigDecimal djAmount;

    /**
     * 剩余资产
     */
    private BigDecimal balance;

    private static final long serialVersionUID = 1L;
}