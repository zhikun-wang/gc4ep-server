package com.r2d2.common.bean;


/**
 Created by tim on 29/06/2017. */
public class RestResponse {

    public static RestResponse create(Object rest) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(200);
        restResponse.setResponse(rest);
        return restResponse;
    }

    private int code;
    private Object response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }


//    @Override
//    public String toString() {
//        return String.format("code[%s],response[%s]", code, ""+response);
//    }
}
