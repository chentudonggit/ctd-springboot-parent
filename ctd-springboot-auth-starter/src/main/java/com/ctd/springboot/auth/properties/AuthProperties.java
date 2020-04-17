package com.ctd.springboot.auth.properties;

import com.ctd.springboot.auth.properties.url.UrlPermissionProperties;

/**
 * 认证
 *
 * @author chentudong
 * @date 2020/3/7 15:32
 * @since 1.0
 */
public class AuthProperties
{
    /**
     * 优先级大于忽略认证配置
     * <p>
     * ${@link PermitProperties#getUrls()} 互斥
     */
    private String[] urls = {};

    /**
     * token自动续签配置 默认redis，其他方式需要自己实现
     */
    private RenewProperties renew = new RenewProperties();

    /**
     * url权限配置
     */
    private UrlPermissionProperties urlPermission = new UrlPermissionProperties();

    public String[] getUrls()
    {
        return urls;
    }

    public void setUrls(String[] urls)
    {
        this.urls = urls;
    }

    public RenewProperties getRenew()
    {
        return renew;
    }

    public void setRenew(RenewProperties renew)
    {
        this.renew = renew;
    }

    public UrlPermissionProperties getUrlPermission()
    {
        return urlPermission;
    }

    public void setUrlPermission(UrlPermissionProperties urlPermission)
    {
        this.urlPermission = urlPermission;
    }
}
