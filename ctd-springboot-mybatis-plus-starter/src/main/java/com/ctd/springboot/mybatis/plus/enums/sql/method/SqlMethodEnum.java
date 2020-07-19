package com.ctd.springboot.mybatis.plus.enums.sql.method;

/**
 * SqlMethodEnum
 *
 * @author chentudong
 * @date 2020/3/8 9:21
 * @since 1.0
 */
public enum SqlMethodEnum {
    /**
     * 删除全部
     */
    DELETE_ALL("deleteAll", "根据条件删除记录", "<script>\nDELETE FROM %s %s\n</script>");

    /**
     * 方法
     */
    private final String method;

    /**
     * 描述
     */
    private final String desc;

    /**
     * sql
     */
    private final String sql;

    SqlMethodEnum(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
