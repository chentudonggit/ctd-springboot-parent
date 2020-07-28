package com.ctd.springboot.common.core.vo.role;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * RoleVO
 *
 * @author chentudong
 * @date 2020/3/7 16:23
 * @since 1.0
 */
@ApiModel("RoleVO")
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 6534130632366133165L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @JsonProperty(value = "id")
    @JSONField(name = "id")
    private String id;

    /**
     * code
     */
    @ApiModelProperty("代码")
    @JsonProperty(value = "code")
    @JSONField(name = "code")
    private String code;

    /**
     * name
     */
    @ApiModelProperty("名称")
    @JsonProperty(value = "name")
    @JSONField(name = "name")
    private String name;

    /**
     * userId
     */
    @ApiModelProperty("用户id")
    @JsonProperty(value = "user_id")
    @JSONField(name = "user_id")
    private String userId;

    /**
     * createTime
     */
    @ApiModelProperty("创建时间")
    @JsonProperty(value = "create_time")
    @JSONField(name = "create_time")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                '}';
    }
}
