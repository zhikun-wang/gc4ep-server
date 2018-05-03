package com.r2d2.common.web.vo;

import java.io.Serializable;
import java.util.Map;

/**
 Created by Zhikun on 14/12/2017. */
public class SqlQueryRequest implements Serializable {

    public enum ConnectorType{
        sparkSql(),elasticSearch();
    }

    private static final long serialVersionUID = 7385697807048144040L;

    private String sql;
    private ConnectorType connectorType;
    private String connectorName;
    private Map<String, Object> properties;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }
}
