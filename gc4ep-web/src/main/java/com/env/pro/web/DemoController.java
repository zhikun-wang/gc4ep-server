package com.env.pro.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 Created by Zhikun on 10/11/2017. */
@RestController
public class DemoController {

    @RequestMapping("admin/demo/**")
    public Object home(HttpServletRequest request) {
        Map<String, Object> result = new HashMap();
        result.put("url", request.getRequestURL());
        return result;
    }
}
