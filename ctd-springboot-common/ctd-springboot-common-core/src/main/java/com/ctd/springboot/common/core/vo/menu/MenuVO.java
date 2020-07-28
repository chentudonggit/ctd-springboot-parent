package com.ctd.springboot.common.core.vo.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.springboot.common.core.enums.method.MethodEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 *
 * @author chentudong
 * @date 2020/3/9 9:03
 * @since 1.0
 */
@ApiModel("MenuVO")
public class MenuVO implements Serializable {
    private static final long serialVersionUID = -5335524456445365347L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @JsonProperty(value = "id")
    @JSONField(name = "id")
    private String id;

    /**
     * parentId
     */
    @ApiModelProperty("父id")
    @JsonProperty(value = "parent_id")
    @JSONField(name = "parent_id")
    private String parentId;

    /**
     * name
     */
    @ApiModelProperty("名称")
    @JsonProperty(value = "name")
    @JSONField(name = "name")
    private String name;

    /**
     * url
     */
    @ApiModelProperty("url")
    @JsonProperty(value = "url")
    @JSONField(name = "url")
    private String url;

    /**
     * path
     */
    @ApiModelProperty("path")
    @JsonProperty(value = "path")
    @JSONField(name = "path")
    private String path;

    /**
     * sort
     */
    @ApiModelProperty(value = "排序", example = "0")
    @JsonProperty(value = "sort", defaultValue = "0")
    @JSONField(name = "sort")
    private Integer sort;

    /**
     * type
     */
    @ApiModelProperty(value = "type", example = "0")
    @JsonProperty(value = "type", defaultValue = "0")
    @JSONField(name = "type")
    private Integer type;

    /**
     * hidden
     */
    @ApiModelProperty("隐藏 = false")
    @JsonProperty(value = "hidden")
    @JSONField(name = "hidden")
    private Boolean hidden;

    /**
     * method
     */
    @ApiModelProperty("方法类型 {GET、POST、PUT、DELETE}")
    @JsonProperty(value = "method")
    @JSONField(name = "method")
    private MethodEnum method;

    /**
     * roleIds
     */
    @ApiModelProperty("角色ids")
    @JsonProperty(value = "role_ids")
    @JSONField(name = "role_ids")
    private Set<String> roleIds;

    /**
     * menuIds
     */
    @ApiModelProperty("菜单ids")
    @JsonProperty(value = "menu_ids")
    @JSONField(name = "menu_ids")
    private Set<String> menuIds;

    /**
     * tenantId
     */
    @JsonInclude
    @JsonProperty(value = "tenant_id")
    @JSONField(name = "tenant_id")
    private String tenantId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonProperty(value = "create_time")
    @JSONField(name = "create_time")
    private Date createTime;

    /**
     * children
     */
    @ApiModelProperty("children")
    @JsonProperty(value = "children")
    @JSONField(name = "children")
    private List<MenuVO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public MethodEnum getMethod() {
        return method;
    }

    public void setMethod(MethodEnum method) {
        this.method = method;
    }

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Set<String> menuIds) {
        this.menuIds = menuIds;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", sort=" + sort +
                ", type=" + type +
                ", hidden=" + hidden +
                ", method=" + method +
                ", roleIds=" + roleIds +
                ", menuIds=" + menuIds +
                ", tenantId='" + tenantId + '\'' +
                ", createTime=" + createTime +
                ", children=" + children +
                '}';
    }
}
