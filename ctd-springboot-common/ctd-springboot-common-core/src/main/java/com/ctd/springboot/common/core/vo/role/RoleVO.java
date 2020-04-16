package com.ctd.mall.framework.common.core.vo.role;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * RoleVO
 *
 * @author chentudong
 * @date 2020/3/7 16:23
 * @since 1.0
 */
public class RoleVO implements Serializable
{
    private static final long serialVersionUID = 6534130632366133165L;

    /**
     * id
     */
    @JsonProperty(value = "id")
    @JSONField(name = "id")
    private String id;

    /**
     * code
     */
    @JsonProperty(value = "code")
    @JSONField(name = "code")
    private String code;

    /**
     * name
     */
    @JsonProperty(value = "name")
    @JSONField(name = "name")
    private String name;

    /**
     * userId
     */
    @JsonProperty(value = "user_id")
    @JSONField(name = "user_id")
    private Long userId;

    /**
     * createTime
     */
    @JsonProperty(value = "create_time")
    @JSONField(name = "create_time")
    private Date createTime;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString()
    {
        return "RoleVO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                '}';
    }
}
