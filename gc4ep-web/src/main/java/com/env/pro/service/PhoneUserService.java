package com.env.pro.service;

import com.env.pro.web.vo.BagOperatorView;
import com.r2d2.common.service.BaseService;

import java.util.List;


/**
 Created by Zhikun on 20/12/2017. */
public interface PhoneUserService extends BaseService {

    List<BagOperatorView> userBagHis(String userId);

}
