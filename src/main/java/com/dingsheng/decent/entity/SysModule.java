package com.dingsheng.decent.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
@Data
public class SysModule implements Serializable {
    private Integer id;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接
     */
    private String linkUrl;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级ID
     */
    private Integer parentID;

    /**
     * 排序 越大越靠前
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;


}