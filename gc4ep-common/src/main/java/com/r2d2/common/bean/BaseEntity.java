package com.r2d2.common.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 Created by tim on 07/07/2017. */
public abstract class BaseEntity implements Serializable{

    private static final long serialVersionUID = 5461860944144033360L;

    private String id;
    private Date createTime;
    private Date lastUpdated;
    private Integer version;

    public String tableName() {
        return this.getClass().getSimpleName();
    }

    public void nextId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
