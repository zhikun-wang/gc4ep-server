package com.r2d2.common.web.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 Created by Zhikun on 14/12/2017. */
public class SqlQueryResponse implements Serializable {

    private static final long serialVersionUID = -6468658475457158392L;

    private List<Map<String, Object>> data;
    private Map<String, String> metadata;

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
