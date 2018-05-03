package com.r2d2.common.service.impl;


import com.alibaba.fastjson.JSON;
import com.r2d2.common.EnumBeanPropertySqlParameterSource;
import com.r2d2.common.bean.BaseEntity;
import com.r2d2.common.exception.ServiceException;
import com.r2d2.common.service.BaseService;
import com.r2d2.common.util.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 Created by tim on 22/06/2017. */
public class BaseServiceImpl implements BaseService {

    Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Resource
    protected JdbcTemplate jdbcTemplate;

    @Resource
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public <T extends BaseEntity> T get(String id, Class<T> t) {
        String sql = SqlHelper.sqlQueryById(t);

        //T tt =jdbcTemplate.queryForObject(sql,t,id);

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(t), id);
    }

    @Override
    public int count(Class t) {
        String sql = SqlHelper.sqlCount(t);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public <T extends BaseEntity> List<T> paging(T t, Map<String, Object> params, int start, int limit) {
        return null;
    }

    @Override
    public int save(BaseEntity o) {
        o.setVersion(1);
        if (StringUtils.isEmpty(o.getId()))
            o.nextId();
        String sql = SqlHelper.sqlInsert(o);
        Object[] values = SqlHelper.modelValues();
        if (logger.isDebugEnabled()) {
            logger.debug("sql == " + sql);
            logger.debug("values == " + JSON.toJSONString(values));
        }
        return namedParameterJdbcTemplate.update(sql, new EnumBeanPropertySqlParameterSource(o));
    }

    @Override
    public int save(List list) {
        //使用batchUpdate ,以后修改
        int r = 0;
        for (Object b : list) {
            if (b instanceof BaseEntity) {
                r += save((BaseEntity) b);
            }
        }
        return r;
    }

    @Override
    public int update(BaseEntity o) {
        String sql = SqlHelper.sqlUpdate(o);
        if (logger.isDebugEnabled()) {
           loggerSql(sql);
        }
        return namedParameterJdbcTemplate.update(sql,new EnumBeanPropertySqlParameterSource(o));
    }

    @Override
    public int update(List list) {
        int r = 0;
        for (Object b : list) {
            if (b instanceof BaseEntity) {
                r += update((BaseEntity) b);
            }
        }
        return r;
    }

    @Override
    public int merge(BaseEntity t) {
        if (null == t.getId()) {
            return save(t);
        } else
            return update(t);
    }

    @Override
    public int merge(List list) {
        int r = 0;
        for (Object b : list) {
            if (b instanceof BaseEntity) {
                r += merge((BaseEntity) b);
            }
        }
        return r;
    }

    @Override
    public int delete(String id, Class t) {
        String sql = SqlHelper.sqlDelete(t);
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int delete(BaseEntity t) {
        try {
            String sql = SqlHelper.sqlDelete(t);
            SqlParameterSource sqlPS = new EnumBeanPropertySqlParameterSource(t);
            if (logger.isDebugEnabled()) {
                logger.debug("sql == " + sql);
                logger.debug("values == " + JSON.toJSONString(sqlPS));
            }
            return namedParameterJdbcTemplate.update(sql, sqlPS);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public <T extends BaseEntity> List<T> query(T t) {
        String sql = SqlHelper.sqlQuery(t, false);
        loggerSql(sql);
        List<T> tt = namedParameterJdbcTemplate.query(sql, new EnumBeanPropertySqlParameterSource(t), new BeanPropertyRowMapper(t.getClass()));
        return tt;
    }

    void loggerSql(String sql, Object... values) {
        if (logger.isDebugEnabled()) {
            logger.debug("sql == " + sql);
            logger.debug("values == " + JSON.toJSONString(values));
        }
    }
}
