package com.env.pro.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.r2d2.common.service.BaseDbService;
import com.r2d2.common.service.BaseService;
import com.r2d2.common.service.impl.BaseDbServiceImpl;
import com.r2d2.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by tim on 23/06/2017.
 */
@Configuration
public class DatasourceConfig {

    @Autowired
    private Environment pm;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(pm.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(pm.getProperty("spring.datasource.url"));
        dataSource.setUsername(pm.getProperty("spring.datasource.username"));//用户名
        dataSource.setPassword(pm.getProperty("spring.datasource.password"));//密码
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }

    @Bean
    public BaseDbService baseDbService(){
        return new BaseDbServiceImpl();
    }

    @Bean
    public BaseService baseService(){
        return new BaseServiceImpl();
    }


}
