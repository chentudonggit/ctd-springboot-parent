package com.ctd.springboot.mybatis.plus.handler.json.array;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AbstractArrayJsonTypeHandler
 *
 * @author chentudong
 * @date 2020/8/31 19:58
 * @since 1.9.1
 */
public class AbstractArrayJsonTypeHandler<T> extends BaseTypeHandler<List<T>> {
    private final Class<T> type;

    public AbstractArrayJsonTypeHandler(Class<T> type) {
        AssertUtils.isNull(type, "type 不能为空");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJsonString(parameter));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private String toJsonString(Object parameter) {
        if (Objects.isNull(parameter)) {
            return null;
        }
        return JSON.toJSONString(parameter);
    }

    private List<T> parse(String json) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(json, type);
    }
}
