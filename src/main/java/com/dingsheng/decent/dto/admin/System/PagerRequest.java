package com.dingsheng.decent.dto.admin.System;

import lombok.Data;

@Data
public class PagerRequest {

    /**
     * 页码
     */
    public Integer page;

    /**
     * 行数
     */
    public Integer rows;

    public Integer skip;

    public Integer getPage() {
        if (page < 1) page = 1;
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        if (rows < 1) rows = 10;
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getSkip() {
        return (page - 1) * rows;
    }
}