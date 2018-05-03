package com.env.pro.web;

import com.r2d2.common.bean.BaseEntity;
import com.r2d2.common.service.BaseService;
import com.r2d2.common.web.RestPager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 Created by Zhikun on 21/11/2017. */
public interface IBaseController<T extends BaseEntity> {

    BaseService getBaseService();

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取所有数据")
    List<T> all();

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("增加数据")
    default T post(@ApiParam @RequestBody T record) {
        getBaseService().save(record);
        return record;
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取数据")
    T get(@PathVariable String id);

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改表数据")
    default void put(@PathVariable String id, @RequestBody T record) {
        record.setId(id);
        getBaseService().update(record);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除数据")
    void delete(@PathVariable String id);

    @RequestMapping(value = "{currentPage}/{pageSize}", method = RequestMethod.GET)
    @ApiOperation("分页查询")
    RestPager pager(@PathVariable int currentPage, @PathVariable int pageSize);


}
