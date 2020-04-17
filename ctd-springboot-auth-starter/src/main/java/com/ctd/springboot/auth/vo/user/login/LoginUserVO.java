package com.ctd.springboot.auth.vo.user.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.springboot.common.core.vo.user.UserVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * LoginUserVO
 *
 * @author chentudong
 * @date 2020/3/8 8:31
 * @since 1.0
 */
public class LoginUserVO extends UserVO implements SocialUserDetails
{
    private static final long serialVersionUID = 2845676515016887521L;
    /**
     * 权限集合
     */
    @JsonProperty(value = "permissions")
    @JSONField(name = "permissions")
    private Set<String> permissions;

    /**
     * 权限重写
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(super.getRoles()))
        {
            super.getRoles().parallelStream().forEach(role -> collection.add(new SimpleGrantedAuthority(role.getCode())));
        }
        return collection;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return getEnabled();
    }

    @Override
    public String getUserId()
    {
        return getOpenId();
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public String toString()
    {
        return "LoginUserVO{" +
                "permissions=" + permissions +
                '}';
    }
}
