package com.env.pro.web.vo;

import com.r2d2.common.bean.BaseEntity;

import java.util.Date;

/**
 Created by Zhikun on 20/12/2017. */
public class BagOperator extends BaseEntity {

    public enum OperatorType {
        borrowBag, returnBag;
    }

    private String userId;
    private OperatorType operatorType;
    private String deviceId;
    private Date borrowTime;
    private Date returnTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
}
