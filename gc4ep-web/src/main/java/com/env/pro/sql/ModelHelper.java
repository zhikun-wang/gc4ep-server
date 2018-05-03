package com.env.pro.sql;


import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

import static com.env.pro.sql.ModelHelper.ModelConstant.*;

/**
 Created by tim on 22/06/2017. */
public class ModelHelper {

    private static ThreadLocal<Object[]> threadLocalValues = new ThreadLocal<>();

    public static String tablePre() {
        return "kong_";
    }

    public interface ModelConstant {
        String ID = "id";
        String CREATE_TIME = "create_time";
        String COLUMN_NAME = "column_name";
        String LAST_UPDATED = "last_updated";
        String VERSION = "version";
    }

    private static Set<String> ignoreFields() {
        return new HashSet<String>() {{
            add(CREATE_TIME);
            add(LAST_UPDATED);
            add("class");
        }};
    }

    public static boolean isExistPK(Map<String, Object> model) {
        return model.containsKey(ID);
    }

    public static String getPK(Map<String, Object> model) {
        return null == model.get(ID) ? "" : model.get(ID).toString();
    }

    public static String sqlQuery(String modelName, List<Map<String, Object>> modelFields) {
        return sqlQuery(modelName, modelFields, 0, 100);
    }

    public static String sqlQuery(String modelName, List<Map<String, Object>> modelFields, int start, int limit) {
        boolean isOrder = false;
        for (Map<String, Object> field : modelFields) {
            if (CREATE_TIME.equals(field.get(COLUMN_NAME))) {
                isOrder = true;
                break;
            }
        }
        return "select * from " + modelName + (isOrder ? " order by " + CREATE_TIME + " DESC" : "") + " LIMIT " + start + " , " + limit;
    }


    public static String sqlQuery(String modelName, List<Map<String, Object>> modelFields, Map<String, Object> params) {
        boolean isOrder = false;
        for (Map<String, Object> field : modelFields) {
            if (CREATE_TIME.equals(field.get(COLUMN_NAME))) {
                isOrder = true;
                break;
            }
        }

        StringBuilder valuesRep = new StringBuilder("values(");
        List<Object> values = new ArrayList<>();
        if (null != params && !params.isEmpty()) {
            modelFields.stream().filter(field -> params.containsKey(field.get("column_name") + "")).forEach(field -> {
                valuesRep.append(field.get("column_name") + " ,");
                values.add(params.get(field.get("column_name") + ""));
            });
        }
        StringBuilder sql = new StringBuilder("select * from  " + modelName);
        if (valuesRep.length() > 0) {
            sql.append(valuesRep.substring(0, valuesRep.length() - 1));
        }
        threadLocalValues.set(values.toArray());
        return (isOrder ? " order by " + CREATE_TIME + " DESC" : "") + " LIMIT 100";
    }


    //table_name,column_name,column_type
    public static String sqlInsert(String modelName, List<Map<String, Object>> modelFields, Map<String, Object> model) {
        StringBuilder sql = new StringBuilder("insert into " + modelName + "(");
        StringBuilder valuesRep = new StringBuilder("values(");
        List<Object> values = new ArrayList<>();
        for (Map<String, Object> field : modelFields) {
            String f = field.get(COLUMN_NAME).toString();
            if (ignoreFields().contains(f)) {
                continue;
            }
            if (model.containsKey(f)) {
                sql.append(f + ",");
                valuesRep.append("?,");
                values.add(model.get(f));
            }
        }
        threadLocalValues.set(values.toArray());
        return sql.substring(0, sql.length() - 1) + ") " + valuesRep.substring(0, valuesRep.length() - 1) + ")";
    }

    public static boolean isValidField(Method m) {
        if (m.getReturnType().equals(List.class) || m.getReturnType().equals(Map.class)) return false;

        if (m.getName().startsWith("get")) {
            return true;
        }
        return false;
    }


    public static Object[] modelValues() {
        return threadLocalValues.get();
    }

    public static String sqlDelete(String modelName, List<Map<String, Object>> modelFields) {
        return "delete from " + modelName + " where " + ID + " = ? ";
    }

    public static String sqlCount(String modelName) {
        return "select count(1) from " + modelName;
    }


    static public String tableColumnName(String field) {
        return StringUtils.uncapitalize(field);
        //return camelToUnderline(field);
    }

    static public Set<String> allColumn(Class c) {
        Set<String> columns = new HashSet<>();

        for (Method m : c.getMethods()) {
            if (isValidField(m)) {
                String columnName = tableColumnName(m.getName().substring(3));
                if ("class".equalsIgnoreCase(columnName)) {
                    continue;
                }
                columns.add(columnName);
            }
        }
        return columns;
    }


    private static final char UNDERLINE = '_';

    private static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                if (0 != i)
                    sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
