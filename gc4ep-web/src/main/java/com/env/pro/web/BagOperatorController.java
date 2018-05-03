package com.env.pro.web;

import com.env.pro.service.BagOperatorService;
import com.env.pro.web.vo.BagOperator;
import com.env.pro.web.vo.PhoneUser;
import com.env.pro.web.vo.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 Created by Zhikun on 20/12/2017. */
@RestController
@RequestMapping("admin/bag")
public class BagOperatorController {

    @Autowired
    BagOperatorService bagOperatorService;


    @RequestMapping(value = "borrow", method = RequestMethod.POST)
    public RestResponse borrow(@RequestBody BagOperator operator) {
        return RestResponse.success(bagOperatorService.borrow(operator));
    }

    @RequestMapping(value = "return", method = RequestMethod.POST)
    public RestResponse returnBag(@RequestBody BagOperator operator) {
        return RestResponse.success(bagOperatorService.returnBag(operator));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestBody PhoneUser user) {
        PhoneUser query = new PhoneUser();
        query.setUserPhone(user.getUserPhone());
        List<PhoneUser> users = bagOperatorService.query(query);
        if (users.isEmpty()) {
            user.setBorrowBagCount(0);
            user.setCompletedBagCount(0);
            bagOperatorService.save(user);
            return RestResponse.success(user);
        } else if (users.get(0).getUserPassword().equals(user.getUserPassword())) {
            return RestResponse.success(users.get(0));
        } else {
            return RestResponse.failure("密码错误");
        }
    }
}
