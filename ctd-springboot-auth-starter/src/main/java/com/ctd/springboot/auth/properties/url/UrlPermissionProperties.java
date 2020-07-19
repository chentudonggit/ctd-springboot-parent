package com.ctd.springboot.auth.properties.url;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限配置
 *
 * @author chentudong
 * @date 2020/3/7 15:35
 * @since 1.0
 */
public class UrlPermissionProperties {
    /**
     * 开启url级别权限
     */
    private Boolean enable = false;

    /**
     * 白名单
     * {@link UrlPermissionProperties#enable} = true 生效
     */
    private List<String> includeClientIds = new ArrayList<>(0);

    /**
     * 黑名单
     */
    private List<String> exclusiveClientIds = new ArrayList<>(0);

    /**
     * 登录后可以访问
     */
    private String[] ignoreUrls = {};

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getIncludeClientIds() {
        return includeClientIds;
    }

    public void setIncludeClientIds(List<String> includeClientIds) {
        this.includeClientIds = includeClientIds;
    }

    public List<String> getExclusiveClientIds() {
        return exclusiveClientIds;
    }

    public void setExclusiveClientIds(List<String> exclusiveClientIds) {
        this.exclusiveClientIds = exclusiveClientIds;
    }

    public String[] getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(String[] ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
