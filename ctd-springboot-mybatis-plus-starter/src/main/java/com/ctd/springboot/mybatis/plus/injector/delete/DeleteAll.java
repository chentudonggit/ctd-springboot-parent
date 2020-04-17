package com.ctd.springboot.mybatis.plus.injector.delete;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.ctd.springboot.mybatis.plus.enums.sql.method.SqlMethodEnum;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * DeleteAll
 *
 * @author chentudong
 * @date 2020/3/8 9:20
 * @since 1.0
 */
public class DeleteAll extends AbstractMethod
{
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo)
    {
        String sql;
        SqlMethodEnum sqlMethod = SqlMethodEnum.DELETE_ALL;
        if (tableInfo.isLogicDelete())
        {
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo, sqlWhereEntityWrapper(true, tableInfo));
        } else
        {
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo));
        }
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), languageDriver.createSqlSource(configuration, sql, modelClass));
    }
}
