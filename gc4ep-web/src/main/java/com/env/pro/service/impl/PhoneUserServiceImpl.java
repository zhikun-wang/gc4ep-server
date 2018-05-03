package com.env.pro.service.impl;

import com.env.pro.service.PhoneUserService;
import com.env.pro.web.vo.BagOperatorView;
import com.r2d2.common.service.impl.BaseServiceImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Created by Zhikun on 20/12/2017. */
@Service
public class PhoneUserServiceImpl extends BaseServiceImpl implements PhoneUserService {


    String sqlUserBagHis = "select d.name as device_name ,b.* from bag_operator b left join device d on b.device_id = d.id where b.user_id = :user_id ";

    @Override
    public List<BagOperatorView> userBagHis(String userId) {
        Map<String, Object> params = new HashMap();
        params.put("user_id", userId);
        List<BagOperatorView> result = namedParameterJdbcTemplate.query(sqlUserBagHis, params, new BeanPropertyRowMapper(BagOperatorView.class));
        return result;
    }
}
