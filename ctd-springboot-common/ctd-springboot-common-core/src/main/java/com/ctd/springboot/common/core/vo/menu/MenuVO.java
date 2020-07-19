package com.ctd.springboot.common.core.vo.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.springboot.common.core.enums.method.MethodEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class MenuVO implements Serializable {
    private static final long serialVersionUID = -5335524456445365347L;

    /**
     * id
     */
    @JsonProperty(value = "id")
    @JSONField(name = "id")
    private String id;

    /**
     * parentId
     */
    @JsonProperty(value = "parent_id")
    @JSONField(name = "parent_id")
    private String parentId;

    /**
     * name
     */
    @JsonProperty(value = "name")
    @JSONField(name = "name")
    private String name;

    /**
     * url
     */
    @JsonProperty(value = "url")
    @JSONField(name = "url")
    private String url;

    /**
     * path
     */
    @JsonProperty(value = "path")
    @JSONField(name = "path")
    private String path;

    /**
     * sort
     */
    @JsonProperty(value = "sort")
    @JSONField(name = "sort")
    private Integer sort;

    /**
     * type
     */
    @JsonProperty(value = "type")
    @JSONField(name = "type")
    private Integer type;

    /**
     * hidden
     */
    @JsonProperty(value = "hidden")
    @JSONField(name = "hidden")
    private Boolean hidden;

    /**
     * method
     */
    @JsonProperty(value = "method")
    @JSONField(name = "method")
    private MethodEnum method;

    /**
     * roleIds
     */
    @JsonProperty(value = "role_ids")
    @JSONField(name = "role_ids")
    private Set<String> roleIds;

    /**
     * menuIds
     */
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
    @JsonProperty(value = "create_time")
    @JSONField(name = "create_time")
    private Date createTime;

    /**
     * children
     */
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
