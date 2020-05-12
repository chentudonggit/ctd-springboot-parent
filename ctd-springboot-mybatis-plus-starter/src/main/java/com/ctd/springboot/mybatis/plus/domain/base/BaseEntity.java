package com.ctd.springboot.mybatis.plus.domain.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ctd.springboot.common.core.enums.status.StatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 *
 * @author chentudong
 * @date 2020/3/8 9:23
 * @since 1.0
 */
public abstract class BaseEntity<T extends Model<T>> extends Model<T>
{
    private static final long serialVersionUID = -4526116791401550737L;

    /**
     * id
     *
     * @return Serializable
     */
    @Override
    protected abstract Serializable pkVal();

    /**
     * 版本号
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    protected Long version;

    /**
     * status
     */
    @TableLogic
    @TableField(value = "status", condition = " varchar(20) default ENABLE ", fill = FieldFill.INSERT)
    protected StatusEnum status;

    /**
     * createTime
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * updateTime
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;

    /**
     * tipsMsg
     */
    @TableField(exist = false)
    private StringBuilder tipsMsg = new StringBuilder();

    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "updateTime";
    public static final String STATUS = "status";

    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public StatusEnum getStatus()
    {
        return status;
    }

    public void setStatus(StatusEnum status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public void setTipsMsg(Object tipsMsg)
    {
        getTipsMsg().append(tipsMsg);
    }

    public StringBuilder getTipsMsg()
    {
        return tipsMsg;
    }
}
