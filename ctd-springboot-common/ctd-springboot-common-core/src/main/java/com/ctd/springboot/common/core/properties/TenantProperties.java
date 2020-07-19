package com.ctd.springboot.common.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户
 *
 * @author chentudong
 * @date 2020/5/10 20:33
 * @since 1.0
 */
@ConfigurationProperties(prefix = "ctd.tenant")
@RefreshScope
public class TenantProperties {
    /**
     * 是否开启多租户
     */
    private Boolean enable = false;

    /**
     * 配置不进行多租户隔离的表名
     */
    private List<String> ignoreTables = new ArrayList<>();

    /**
     * 配置不进行多租户隔离的sql
     * 需要配置mapper的全路径如：*Mapper.findAll
     */
    private List<String> ignoreSqls = new ArrayList<>();

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getIgnoreTables() {
        return ignoreTables;
    }

    public void setIgnoreTables(List<String> ignoreTables) {
        this.ignoreTables = ignoreTables;
    }

    public List<String> getIgnoreSqls() {
        return ignoreSqls;
    }

    public void setIgnoreSqls(List<String> ignoreSqls) {
        this.ignoreSqls = ignoreSqls;
    }
}
