package com.env.pro.web;

import com.env.pro.web.vo.Device;
import com.env.pro.web.vo.RestResponse;
import com.r2d2.common.service.BaseDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 Created by Zhikun on 15/11/2017. */
@RequestMapping("admin/devices")
@RestController
@Api(value = "Scan Device", description = "设备扫码后台服务")
public class DevicesController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "{id}/lock", method = RequestMethod.GET)
    @ApiOperation(value = "锁定设备")
    public RestResponse lock(@PathVariable String id) {
        String sql = " update devices set status= 'lock' where id = ? and status = 'unlock' ";
        int effectRecord = jdbcTemplate.update(sql, id);
        if (effectRecord == 1) {
            return RestResponse.success();
        } else {
            return RestResponse.failure("effect record == " + effectRecord);
        }
    }


    @RequestMapping(value = "{id}/unlock", method = RequestMethod.GET)
    public RestResponse unlock(@PathVariable String id) {
        String sql = " update devices set status= 'unlock' where id = ? and status = 'lock' ";
        int effectRecord = jdbcTemplate.update(sql, id);
        if (effectRecord == 1) {
            return RestResponse.success();
        } else {
            return RestResponse.failure("effect record == " + effectRecord);
        }
    }

    @Autowired
    BaseDbService baseDbService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取所有数据")
    public RestResponse all() {
        List<Device> devices = baseDbService.query(new Device());
        return RestResponse.success(devices);
    }


}
