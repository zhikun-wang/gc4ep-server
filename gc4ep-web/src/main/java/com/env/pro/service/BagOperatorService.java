package com.env.pro.service;

import com.env.pro.web.vo.BagOperator;
import com.r2d2.common.service.BaseService;

/**
 Created by Zhikun on 20/12/2017. */
public interface BagOperatorService extends BaseService {


    BagOperator borrow(BagOperator operator);

    BagOperator returnBag(BagOperator operator);

}
