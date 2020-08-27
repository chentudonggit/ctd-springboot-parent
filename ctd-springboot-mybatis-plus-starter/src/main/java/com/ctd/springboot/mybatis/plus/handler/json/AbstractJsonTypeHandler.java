package com.ctd.springboot.mybatis.plus.handler.json;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 数据库字段为json 自动转换
 *
 * @author chentudong
 * @date 2020/8/26 22:58
 * @since 1.0
 */
public abstract class AbstractJsonTypeHandler<T> extends BaseTypeHandler<T> {
    private final Class<T> type;

    public AbstractJsonTypeHandler(Class<T> type) {
        AssertUtils.isNull(type, "type 不能为空");
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJsonString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }


    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private String toJsonString(Object parameter) {
        if (Objects.isNull(parameter)) {
            return null;
        }
        return JSON.toJSONString(parameter);
    }

    private T parse(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, type);
    }
}
