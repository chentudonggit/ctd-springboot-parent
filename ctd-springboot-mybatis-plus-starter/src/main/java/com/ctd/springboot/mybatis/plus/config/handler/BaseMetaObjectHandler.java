package com.ctd.springboot.mybatis.plus.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ctd.springboot.common.core.enums.status.StatusEnum;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自动填充
 *
 * @author chentudong
 * @date 2020/3/8 9:16
 * @since 1.0
 */
public class BaseMetaObjectHandler implements MetaObjectHandler
{
    public static final String VERSION = "version";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";
    public static final String STATUS = "status";
    public static final String PARENT_ID = "parentId";

    @Override
    public void insertFill(MetaObject metaObject)
    {
        Date now = new Date();
        fieldValByName(VERSION, 1L, metaObject);
        fieldValByName(PARENT_ID, 0, metaObject);
        fieldValByName(STATUS, StatusEnum.ENABLE, metaObject);
        fieldValByName(CREATE_TIME, now, metaObject);
        fieldValByName(UPDATE_TIME, now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject)
    {
        this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
    }
    private void fieldValByName(String key, Object fieldVal, MetaObject metaObject)
    {
        if (metaObject.hasGetter(key))
        {
            this.setFieldValByName(key, fieldVal, metaObject);
        }
    }
}
