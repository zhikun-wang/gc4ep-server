package com.env.pro.web.vo;

import com.r2d2.common.bean.BaseEntity;

/**
 Created by Zhikun on 20/12/2017. */
public class PhoneUser extends BaseEntity {

    private static final long serialVersionUID = -3863549586420453765L;

    private String userStatus;
    private String userName;
    private String userPassword;
    private String userPhone;
    private Integer borrowBagCount;
    private Integer completedBagCount;
    private Integer currentBagCount;
    private Integer pointSum;

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getBorrowBagCount() {
        return borrowBagCount;
    }

    public void setBorrowBagCount(Integer borrowBagCount) {
        this.borrowBagCount = borrowBagCount;
    }

    public Integer getCompletedBagCount() {
        return completedBagCount;
    }

    public void setCompletedBagCount(Integer completedBagCount) {
        this.completedBagCount = completedBagCount;
    }

    public Integer getCurrentBagCount() {
        return currentBagCount;
    }

    public void setCurrentBagCount(Integer currentBagCount) {
        this.currentBagCount = currentBagCount;
    }

    public Integer getPointSum() {
        return pointSum;
    }

    public void setPointSum(Integer pointSum) {
        this.pointSum = pointSum;
    }
}
