package com.unknown.common.respone;


import java.io.Serializable;

/**
 * 统一响应信息主体
 *
 * @author
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 失败
     */
    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;


    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> success() {
        return new R<>(SUCCESS, null, null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(SUCCESS, null, data);
    }

    public static <T> R<T> success(T data, String msg) {
        return new R<>(SUCCESS, msg, data);
    }

    public static <T> R<T> fail() {
        return new R<>(FAIL, null, null);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(FAIL, msg, null);
    }

    public static <T> R<T> fail(T data) {
        return new R<>(FAIL, null, data);
    }

    public static <T> R<T> fail(T data, String msg) {
        return new R<>(FAIL, msg, data);
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, msg, null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


