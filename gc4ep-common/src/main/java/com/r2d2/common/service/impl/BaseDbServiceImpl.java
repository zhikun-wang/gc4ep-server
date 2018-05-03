package com.r2d2.common.service.impl;


import com.r2d2.common.service.BaseDbService;
import com.r2d2.common.util.SqlHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by tim on 22/06/2017. */
public class BaseDbServiceImpl extends BaseServiceImpl implements BaseDbService {

    static Map<String, List<Map<String, Object>>> modelCache = new HashMap();

    @Override
    public List<Map<String, Object>> queryTable(String modelName) {
        if (modelCache.containsKey(modelName)) {
            return modelCache.get(modelName);
        }

        String sql = "select table_name,column_name,column_type from information_schema.columns where table_schema = 'openapi' and table_name = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, new Object[]{modelName});
        if (!result.isEmpty()) {
            modelCache.put(modelName, result);
        }
        return result;
    }

    @Override
    public boolean isExist(String modelName) {
        return !queryTable(modelName).isEmpty();
    }

    @Override
    public List<Map<String, Object>> allTable() {
        String sql = "select table_name from information_schema.tables where table_schema='openapi' and table_type='base table'";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> refresh() {
        modelCache.clear();
        return allTable();
    }

    @Override
    public List<Map<String, Object>> query(String modelName, Map<String, Object> params) {
        String sql = SqlHelper.sqlQuery(modelName, queryTable(modelName), params);
        Object[] values = SqlHelper.modelValues();
        return jdbcTemplate.queryForList(sql, values);
    }

    @Override
    public List<Map<String, Object>> all(String modelName) {
        return jdbcTemplate.queryForList(SqlHelper.sqlQuery(modelName, queryTable(modelName)));
    }

    @Override
    public int count(String modelName) {
        return jdbcTemplate.queryForObject(SqlHelper.sqlCount(modelName), Integer.class);
    }

    @Override
    public List<Map<String, Object>> paging(String modelName, Map<String, Object> params, int start, int limit) {
        return null;
    }

    @Override
    public int save(String modelName, Map<String, Object> model) {
        if (SqlHelper.isExistPK(model)) {
            delete(modelName, SqlHelper.getPK(model));
        }

        String sql = SqlHelper.sqlInsert(modelName, queryTable(modelName), model);
        Object[] values = SqlHelper.modelValues();
        return jdbcTemplate.update(sql, values);
    }

    @Override
    public int delete(String modelName, String pk) {
        String sql = SqlHelper.sqlDelete(modelName, queryTable(modelName));
        int result = jdbcTemplate.update(sql, new Object[]{pk});
        return result;
    }

}
