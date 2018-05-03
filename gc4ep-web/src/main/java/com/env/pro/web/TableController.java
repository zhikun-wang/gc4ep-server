package com.env.pro.web;

import com.r2d2.common.service.BaseDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by tim on 22/06/2017.
 */
@RestController
@RequestMapping("tables")
public class TableController {

    @Autowired
    private BaseDbService modelService;

    @RequestMapping(value = "{tableName}",method= RequestMethod.GET)
    public List<Map<String, Object>> query(@PathVariable String tableName) {
        return modelService.queryTable(tableName);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Map<String, Object>> all() {
        return modelService.allTable();
    }


    @RequestMapping(value ="refresh",method = RequestMethod.GET)
    public List<Map<String, Object>> refresh() {
        return modelService.refresh();
    }
}
