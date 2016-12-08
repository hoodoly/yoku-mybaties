package com.yoku.mybatis.model;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/12/8
 */
public class PageData<T> implements Serializable {

    private long total;
    private List<T> data;

    public PageData(long total, List<T> data){
        this.total = total;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {

        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
