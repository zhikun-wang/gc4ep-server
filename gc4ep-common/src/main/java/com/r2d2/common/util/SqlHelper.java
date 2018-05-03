package com.r2d2.common.util;

import com.r2d2.common.bean.BaseEntity;
import com.r2d2.common.exception.ServiceException;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.r2d2.common.util.SqlHelper.ModelConstant.*;

/**
 Created by tim on 22/06/2017. */
public class SqlHelper {

    private static ThreadLocal<Object[]> threadLocalValues = new ThreadLocal<>();

    public static String tablePre() {
        return "";
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
        boolean isOrder = false;
        for (Map<String, Object> field : modelFields) {
            if (CREATE_TIME.equals(field.get(COLUMN_NAME))) {
                isOrder = true;
                break;
            }
        }
        return "select * from " + modelName + (isOrder ? " order by " + CREATE_TIME + " DESC" : "") + " LIMIT 100";
    }

    public static String sqlQueryById(Class c) {
        return String.format("select * from %s where %s = ?", tableName(c), ModelConstant.ID);
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

    public static String sqlQuery(BaseEntity e, boolean isOrder) {
        StringBuilder sql = new StringBuilder(" select ");
        Set<String> columns = allColumn(e.getClass());
        sql.append(String.join(", ", columns));
        sql.append(" from " + tableName(e.getClass()));

        StringBuilder whereSql = new StringBuilder();
        for (Method m : e.getClass().getMethods()) {
            if (isValidField(m)) {
                String fieldName = fieldNameFromMethod(m);
                String columnName = toDBColumnName(fieldName);
                if (ignoreFields().contains(columnName)) {
                    continue;
                }
                Object value;
                try {
                    value = m.invoke(e);
                } catch (IllegalAccessException | InvocationTargetException se) {
                    throw new ServiceException(se);
                }
                if (null != value) {
                    whereSql.append(columnName + "= :" + fieldName + " and ");
                }
            }
        }
        if (whereSql.length() > 0) {
            sql.append(" where " + whereSql.substring(0, whereSql.length() - 4));
        }

        if (isOrder)
            sql.append(" order by " + CREATE_TIME + " DESC");
        return sql.toString();
    }

    public static String sqlQuery(BaseEntity e) {
        return sqlQuery(e, true);
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

    public static String sqlInsert(Object o) {
        StringBuilder sql = new StringBuilder("insert into " + tableName(o.getClass()) + "(");
        StringBuilder valuesRep = new StringBuilder("values(");

        for (Method m : o.getClass().getMethods()) {
            if (isValidField(m)) {
                String fieldName = fieldNameFromMethod(m);
                String columnName = toDBColumnName(fieldName);

                if (LAST_UPDATED.equalsIgnoreCase(columnName) || CREATE_TIME.equalsIgnoreCase(columnName)) {
                    sql.append(columnName + ",");
                    valuesRep.append("sysdate(),");
                    continue;
                }

                if (ignoreFields().contains(columnName)) {
                    continue;
                }

                sql.append(columnName + ",");
                valuesRep.append(":" + fieldName + ",");
            }
        }
        return sql.substring(0, sql.length() - 1) + ") " + valuesRep.substring(0, valuesRep.length() - 1) + ")";
    }

    public static String sqlUpdate(BaseEntity o) {
        StringBuilder sql = new StringBuilder("update " + tableName(o.getClass()) + " set ");

        for (Method m : o.getClass().getMethods()) {
            if (isValidField(m)) {
                String fieldName = fieldNameFromMethod(m);
                String columnName = toDBColumnName(fieldName);

                if (LAST_UPDATED.equalsIgnoreCase(columnName)) {
                    sql.append(LAST_UPDATED + " = sysdate() ,");
                    continue;
                }
                if (ModelConstant.VERSION.equalsIgnoreCase(columnName)) {
                    sql.append(ModelConstant.VERSION + " = " + ModelConstant.VERSION + "+1 ,");
                    continue;
                }
                if (ignoreFields().contains(columnName) || ModelConstant.ID.equalsIgnoreCase(columnName)) {
                    continue;
                }

                sql.append(columnName + " = :" + fieldName + " ,");
            }
        }
        StringBuilder whereSql = new StringBuilder(" where " + ModelConstant.ID + " = :id ");
        if (null != o.getVersion()) {
            whereSql.append(" and " + VERSION + " = :version ");
        }
        return sql.substring(0, sql.length() - 1) + " " + whereSql;
    }

    public static Object[] modelValues() {
        return threadLocalValues.get();
    }

    public static String sqlDelete(String modelName, List<Map<String, Object>> modelFields) {
        return "delete from " + modelName + " where " + ID + " = ? ";
    }

    public static String sqlDelete(Class c) {
        return "delete from " + tableName(c) + " where " + ID + " = ? ";
    }

    public static String sqlDelete(BaseEntity o) throws InvocationTargetException, IllegalAccessException {

        String pre = "delete from " + tableName(o.getClass()) + " where ";
        StringBuilder sql = new StringBuilder();
        List<Object> values = new ArrayList<>();


        for (Method m : o.getClass().getMethods()) {
            if (m.getName().startsWith("get")) {
                String fieldName = fieldNameFromMethod(m);
                String columnName = toDBColumnName(fieldName);
                if (ignoreFields().contains(columnName)) {
                    continue;
                }
                if (LAST_UPDATED.equalsIgnoreCase(columnName)) {
                    continue;
                }
                Object value = m.invoke(o);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                sql.append(columnName + " = :" + fieldName + " and ");
                values.add(m.invoke(o));
            }
        }
        if (sql.length() < 1) {
            throw new ServiceException("cannot delete Entity,all properties is null");
        }
        threadLocalValues.set(values.toArray());
        return pre + sql.substring(0, sql.length() - 4);
    }

    public static String sqlCount(String modelName) {
        return "select count(1) from " + modelName;
    }

    public static String sqlCount(Class t) {
        return String.format("select count(1) from %s ", tableName(t));
    }

    static public String tableName(Class c) {
//        Annotation[] anno = c.getAnnotations();
//        if (anno.length > 0) {
//            for (Annotation a : anno) {
//                if (a.annotationType().equals(Table.class)) {
//                    return ((Table) a).name();
//                }
//            }
//        }
        return tablePre() + camelToUnderline(c.getSimpleName());
    }

    static public String fieldNameFromMethod(Method m) {
        return StringUtils.uncapitalize(m.getName().substring(3));
    }

    static public String toDBColumnName(String field) {
        //return StringUtils.uncapitalize(field);
        return camelToUnderline(field);
    }

    static public Set<String> allColumn(Class c) {
        Set<String> columns = new HashSet<>();

        for (Method m : c.getMethods()) {
            if (isValidField(m)) {
                String columnName = toDBColumnName(m.getName().substring(3));
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
