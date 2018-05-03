package com.r2d2.common.service;

import java.util.List;
import java.util.Map;

/**
 Created by Zhikun on 29/11/2017. */
public interface BaseDbService extends BaseService{

    boolean isExist(String modelName);

    List<Map<String, Object>> queryTable(String tableName);

    List<Map<String, Object>> allTable();

    List<Map<String, Object>> refresh();

    List<Map<String, Object>> query(String modelName, Map<String, Object> params);

    List<Map<String, Object>> all(String modelName);

    int count(String modelName);

    List<Map<String, Object>> paging(String modelName, Map<String, Object> params, int start, int limit);

    int save(String modelName, Map<String, Object> model);

    int delete(String modelName, String pk);
}
