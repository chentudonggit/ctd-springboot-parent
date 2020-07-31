package com.ctd.springboot.common.core.constants;

/**
 * CommonConstant
 *
 * @author chentudong
 * @date 2020/5/12 11:22 上午
 * @since 1.0
 */
public interface CommonConstant {
    /**
     * 菜单
     */
    Integer MENU = 1;

    /**
     * 权限
     */
    Integer PERMISSION = 2;

    /**
     * 超级管理员用户名
     */
    String ADMIN_USER_NAME = "admin";

    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";

    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "x-traceId-header";

    /**
     * 负载均衡策略-版本号 信息头
     */
    String C_T_D_VERSION = "c-t-d-version";

    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    String BEARER_TYPE = "Bearer";

}
