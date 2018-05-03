package com.env.pro.web.vo;

/**
 Created by Zhikun on 20/12/2017. */
public class RequestMessage {

    static public RequestMessage create(String name){
        return new RequestMessage(name);
    }

    private RequestMessage(String name){
        this.name =name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
