package com.ctd.springboot.auth.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 无需认证
 *
 * @author chentudong
 * @date 2020/3/7 15:33
 * @since 1.0
 */
public class PermitProperties {
    /**
     * 默认无需认证
     */
    private static final String[] ENDPOINTS = {
            "/oauth/**",
            "/actuator/**",
            "/*/v2/api-docs",
            "/swagger/api-docs",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/druid/**"
    };

    /**
     * 无需认证url 配置
     */
    private String[] urls = {};

    public String[] getUrls() {
        if (Objects.isNull(urls) || urls.length <= 0) {
            return ENDPOINTS;
        }
        List<String> result = Arrays.asList(urls);
        return result.toArray(new String[0]);
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
