package com.ctd.mall.framework.common.core.holder.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 租户
 *
 * @author chentudong
 * @date 2020/3/14 13:45
 * @since 1.0
 */
public class TenantContextHolder
{
    private static final ThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

    /**
     * setTenant
     *
     * @param tenant tenant
     */
    public static void setTenant(String tenant)
    {
        CONTEXT.set(tenant);
    }

    /**
     * getTenant
     *
     * @return String
     */
    public static String getTenant()
    {
        return CONTEXT.get();
    }

    /**
     * clear
     */
    public static void clear()
    {
        CONTEXT.remove();
    }
}
