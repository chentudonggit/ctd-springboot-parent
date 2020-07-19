package com.ctd.springboot.common.core.vo.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.springboot.common.core.vo.role.RoleVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * UserVO
 *
 * @author chentudong
 * @date 2020/3/7 16:22
 * @since 1.0
 */
public class UserVO implements Serializable {
    private static final long serialVersionUID = -8960027113388647737L;
    /**
     * id
     */
    @JsonProperty(value = "id")
    @JSONField(name = "id")
    private String id;

    /**
     * username
     */
    @JsonProperty(value = "user_name")
    @JSONField(name = "user_name")
    private String username;

    /**
     *
     */
    @JsonProperty(value = "pass_word")
    @JSONField(name = "pass_word")
    private String password;

    /**
     *
     */
    @JsonProperty(value = "nick_name")
    @JSONField(name = "nick_name")
    private String nickname;

    /**
     * headImgUrl
     */
    @JsonProperty(value = "head_img_url")
    @JSONField(name = "head_img_url")
    private String headImgUrl;

    /**
     * mobile
     */
    @JsonProperty(value = "mobile")
    @JSONField(name = "mobile")
    private String mobile;

    /**
     * sex
     */
    @JsonProperty(value = "sex")
    @JSONField(name = "sex")
    private Integer sex;

    /**
     * enabled
     */
    @JsonProperty(value = "enabled")
    @JSONField(name = "enabled")
    private Boolean enabled;

    /**
     * type
     */
    @JsonProperty(value = "type")
    @JSONField(name = "type")
    private String type;

    /**
     * openId
     */
    @JsonProperty(value = "open_id")
    @JSONField(name = "open_id")
    private String openId;

    /**
     * roles
     */
    @JsonProperty(value = "roles")
    @JSONField(name = "roles")
    private List<RoleVO> roles;

    /**
     * roleId
     */
    @JsonProperty(value = "role_ids")
    @JSONField(name = "role_ids")
    private List<String> roleIds;

    /**
     * oldPassword
     */
    @JsonProperty(value = "old_pass_word")
    @JSONField(name = "old_pass_word")
    private String oldPassword;

    /**
     * newPassword
     */
    @JsonProperty(value = "new_pass_word")
    @JSONField(name = "new_pass_word")
    private String newPassword;

    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    @JSONField(name = "create_time")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", openId='" + openId + '\'' +
                ", roles=" + roles +
                ", roleIds=" + roleIds +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
