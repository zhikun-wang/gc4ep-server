package com.r2d2.common;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.Types;

/**
 Created by Zhikun on 29/11/2017. */
public class EnumBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {


    public EnumBeanPropertySqlParameterSource(Object object) {
        super(object);
    }


    @Override
    public int getSqlType(String var) {
        int sqlType = super.getSqlType(var);
        if (sqlType == TYPE_UNKNOWN && hasValue(var)) {
            if (null != getValue(var) && getValue(var).getClass().isEnum()) {
                sqlType = Types.VARCHAR;
            }
        }
        return sqlType;
    }

}
