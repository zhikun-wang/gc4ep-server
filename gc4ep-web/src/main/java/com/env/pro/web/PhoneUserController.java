package com.env.pro.web;

import com.env.pro.service.PhoneUserService;
import com.env.pro.web.vo.PhoneUser;
import com.env.pro.web.vo.RestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 Created by Zhikun on 20/12/2017. */
@RestController
@RequestMapping("admin/phoneUser")
public class PhoneUserController {

    @Autowired
    PhoneUserService phoneUserService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取所有数据")
    RestResponse all() {
        return RestResponse.success(phoneUserService.query(new PhoneUser()));
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    RestResponse login(@RequestBody PhoneUser phoneUser) {
        PhoneUser query = new PhoneUser();
        query.setUserPhone(phoneUser.getUserPhone());
        List<PhoneUser> result = phoneUserService.query(query);
        if (result.isEmpty()) {
            phoneUser.setBorrowBagCount(0);
            phoneUser.setCompletedBagCount(0);
            phoneUser.setCurrentBagCount(0);
            phoneUser.setPointSum(0);
            phoneUserService.save(phoneUser);
        } else {
            phoneUser = result.get(0);
        }
        return RestResponse.success(phoneUser);
    }

    @RequestMapping(value = "{userId}/bag", method = RequestMethod.GET)
    public RestResponse userBagHis(@PathVariable("userId") String userId) {
        return RestResponse.success(phoneUserService.userBagHis(userId));
    }


}
