package com.r2d2.common.service;


import com.r2d2.common.bean.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by tim on 22/06/2017.
 */
public interface BaseService {



    int count(Class t);

    <T extends BaseEntity> List<T> paging(T t, Map<String, Object> params, int start, int limit);

    <T extends BaseEntity> List<T> query(T t);

    <T extends BaseEntity> T get(String id, Class<T> t);

    int save(BaseEntity t);

    int save(List list);

    int update(BaseEntity t);

    int update(List list);

    int merge(BaseEntity t);

    int merge(List list);

    int delete(String id, Class t);

    int delete(BaseEntity t);


}
