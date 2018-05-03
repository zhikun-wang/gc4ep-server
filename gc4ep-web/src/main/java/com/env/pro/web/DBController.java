package com.env.pro.web;

import com.env.pro.exception.ModelNotFoundException;
import com.env.pro.web.vo.RestResponse;
import com.r2d2.common.service.BaseDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 Created by Zhikun on 10/11/2017. */
@RestController
@RequestMapping("admin")
public class DBController {

    @Autowired
    BaseDbService baseService;

    @RequestMapping(value = "/{modelName}", method = RequestMethod.GET)
    public RestResponse all(@PathVariable String modelName) {
        return RestResponse.success(baseService.all(modelName));
    }

    @RequestMapping(value = "/{modelName}/{page}/{pageSize}", method = RequestMethod.GET)
    public RestResponse paging(@PathVariable String modelName, @PathVariable Integer page, @PathVariable Integer pageSize) {
        return RestResponse.success(baseService.paging(modelName, null, page * pageSize, pageSize));
    }

    @RequestMapping(value = "/{modelName}/query", method = RequestMethod.POST)
    public RestResponse query(@PathVariable String modelName, @RequestBody Map<String, Object> params) {
        return RestResponse.success(baseService.query(modelName, params));
    }

    @RequestMapping(value = "/{modelName}/count", method = RequestMethod.GET)
    public RestResponse count(@PathVariable String modelName) {
        return RestResponse.success(baseService.count(modelName));
    }

    @RequestMapping(value = "/{modelName}", method = RequestMethod.POST)
    public Object post(@PathVariable String modelName, @RequestBody Map<String, Object> model) {
        return baseService.save(modelName, model);
    }

    @RequestMapping(value = "/{modelName}", method = RequestMethod.PUT)
    public Object put(@PathVariable String modelName, @RequestBody Map<String, Object> model) {
        return baseService.save(modelName, model);
    }

    @RequestMapping(value = "/{modelName}/{pk}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable String modelName, @PathVariable String pk) {
        return baseService.delete(modelName, pk);
    }


    @ExceptionHandler(ModelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResponse modelNotFound(ModelNotFoundException e) {
        return RestResponse.failure("not found model", "10100");
    }


}
