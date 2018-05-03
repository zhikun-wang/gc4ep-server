package com.env.pro.service.impl;

import com.env.pro.service.BagOperatorService;
import com.env.pro.util.DeviceOperatorUtil;
import com.env.pro.util.exception.SocketOperatorException;
import com.env.pro.web.vo.BagOperator;
import com.env.pro.web.vo.Device;
import com.env.pro.web.vo.PhoneUser;
import com.r2d2.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 Created by Zhikun on 20/12/2017. */
@Service
public class BagOperatorServiceImpl extends BaseServiceImpl implements BagOperatorService {

    private boolean isThrowDeviceError = false;

    public BagOperator borrow(BagOperator operator) {
        //设备袋子
        Device device = get(operator.getDeviceId(), Device.class);
        device.setBagCount(device.getBagCount() - 1);
        update(device);

        PhoneUser phoneUser = get(operator.getUserId(), PhoneUser.class);
        phoneUser.setBorrowBagCount(phoneUser.getBorrowBagCount() + 1);
        phoneUser.setCurrentBagCount(phoneUser.getCurrentBagCount() + 1);
        update(phoneUser);

        operator.setBorrowTime(new Date());
        operator.setOperatorType(BagOperator.OperatorType.borrowBag);
        save(operator);


        try {
            DeviceOperatorUtil.bagOut(device.getDeviceIp());
        } catch (IOException | SocketOperatorException e) {
            e.printStackTrace();
            if (isThrowDeviceError)
                throw new RuntimeException(e);
        }
        return operator;
    }

    public BagOperator returnBag(BagOperator operator) {


        PhoneUser phoneUser = get(operator.getUserId(), PhoneUser.class);
        phoneUser.setCompletedBagCount(phoneUser.getCompletedBagCount() + 1);
        if (phoneUser.getCurrentBagCount() > 0) {
            phoneUser.setCurrentBagCount(phoneUser.getCurrentBagCount() - 1);
            phoneUser.setPointSum(phoneUser.getPointSum() + 10);
        } else {
            phoneUser.setPointSum(phoneUser.getPointSum() + 5);
        }
        update(phoneUser);

        operator.setReturnTime(new Date());
        operator.setOperatorType(BagOperator.OperatorType.returnBag);
        save(operator);

        Device device = get(operator.getDeviceId(), Device.class);
        try {
            DeviceOperatorUtil.bagIn(device.getDeviceIp());
        } catch (IOException | SocketOperatorException e) {
            e.printStackTrace();
            if (isThrowDeviceError)
                throw new RuntimeException(e);
        }

        return operator;
    }

}
