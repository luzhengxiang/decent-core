package com.dingsheng.decent.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * 资金流水表
 */

@Data
@Table(name = "assetsiorecords")
public class AssetsIoRecords {
    @Id
    private Long id;
    @Column(name = "accountId")
    private Long accountId;
    @Column(name = "cashFlowTypeID")
    private Long cashFlowTypeID;
    @Column(name = "CashFlowSourceID")
    private Long cashFlowSourceID;
    @Column(name = "recordTime")
    private Date recordTime;
    @Column(name = "ioType")
    private String ioType;
    @Column(name = "ioTypeId")
    private Long ioTypeId;
    private BigDecimal amount;
    private BigDecimal balance;
    private String remark;


}