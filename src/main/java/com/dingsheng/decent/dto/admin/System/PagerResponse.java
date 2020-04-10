package com.dingsheng.decent.dto.admin.System;

import java.util.List;

public class PagerResponse<T> {

    private long total;

    private List<T> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> _rows) {
        this.rows = _rows;
    }
}