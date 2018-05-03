package com.r2d2.common.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by Zhikun on 24/11/2017. */
public class RestTree {

    private String id;
    private String name;
    private List<RestTree> children = new ArrayList<>();
    private Map<String, Object> extraInfo;

    public RestTree(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RestTree() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestTree> getChildren() {
        return children;
    }

    public void setChildren(List<RestTree> children) {
        this.children = children;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }
}
