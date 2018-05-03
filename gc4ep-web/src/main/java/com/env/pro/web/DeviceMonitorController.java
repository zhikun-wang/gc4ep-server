package com.env.pro.web;

import com.env.pro.util.SocketServer;
import com.env.pro.web.vo.RestResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 Created by Zhikun on 13/01/2018. */

@RestController
@RequestMapping("admin/monitor")
public class DeviceMonitorController {

    @RequestMapping(value = "device", method = RequestMethod.GET)
    public RestResponse onLineDevice() {
        return RestResponse.success(SocketServer.allSocket.keySet());
    }

}
