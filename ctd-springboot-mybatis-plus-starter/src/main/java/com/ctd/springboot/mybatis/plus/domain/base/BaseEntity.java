package com.ctd.springboot.mybatis.plus.domain.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 *
 * @author chentudong
 * @date 2020/3/8 9:23
 * @since 1.0
 */
public abstract class BaseEntity<T extends Model> extends Model
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
     * status
     */
    @TableLogic
    @TableField(value = "status", condition = "int(5) default 0 ", fill = FieldFill.INSERT)
    protected Integer status;

    /**
     * remark
     */
    @TableField("remark")
    protected String remark;

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

    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "updateTime";
    public static final String STATUS = "status";

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
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
}
