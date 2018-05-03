package com.env.pro.web.vo;

/**
 Created by tim on 27/06/2017. */
public class RestResponse {

    public static RestResponse success(Object data) {
        RestResponse rb = new RestResponse();
        rb.success = true;
        rb.data = data;
        return rb;
    }

    public static RestResponse success() {
        RestResponse rb = new RestResponse();
        rb.success = true;
        return rb;
    }

    public static RestResponse failure(String msg, String code) {
        RestResponse rb = new RestResponse();
        rb.success = false;
        rb.msg = msg;
        rb.code = code;
        return rb;
    }

    public static RestResponse failure(String msg) {
        RestResponse rb = new RestResponse();
        rb.success = false;
        rb.msg = msg;
        rb.code = "10001";
        return rb;
    }

    private boolean success;
    private Object data;
    private String msg;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
