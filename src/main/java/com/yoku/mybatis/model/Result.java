package com.yoku.mybatis.model;

import java.io.Serializable;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/12/14
 */
public class Result<T> implements Serializable {

    private String error;
    private Boolean isSuccess;
    private T data;


    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<T>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String error) {
        Result<T> resp = new Result<T>();
        resp.setError(error);
        return resp;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.isSuccess = Boolean.FALSE;
        this.error = error;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.isSuccess = Boolean.TRUE;
        this.data = data;
    }
}
