package com.ctd.springboot.auth.properties;

import java.util.ArrayList;
import java.util.List;

/**
 * 续签
 *
 * @author chentudong
 * @date 2020/3/7 15:32
 * @since 1.0
 */
public class RenewProperties
{
    /**
     * 是否开启token自动续签
     * 默认redis，其他方式需要自己实现
     */
    private Boolean enable = false;

    /**
     * {@link RenewProperties#enable} true  生效
     * 白名单 需要自动续签
     */
    private List<String> includeClientIds = new ArrayList<>(0);

    /**
     * {@link RenewProperties#enable} true  生效
     * 黑名单 不需要自动续签
     */
    private List<String> exclusiveClientIds = new ArrayList<>();

    /**
     * 续签时间比例，当前剩余时间小于小于过期总时长的50%则续签
     */
    private Double timeRatio = 0.5;

    public Boolean getEnable()
    {
        return enable;
    }

    public void setEnable(Boolean enable)
    {
        this.enable = enable;
    }

    public List<String> getIncludeClientIds()
    {
        return includeClientIds;
    }

    public void setIncludeClientIds(List<String> includeClientIds)
    {
        this.includeClientIds = includeClientIds;
    }

    public List<String> getExclusiveClientIds()
    {
        return exclusiveClientIds;
    }

    public void setExclusiveClientIds(List<String> exclusiveClientIds)
    {
        this.exclusiveClientIds = exclusiveClientIds;
    }

    public Double getTimeRatio()
    {
        return timeRatio;
    }

    public void setTimeRatio(Double timeRatio)
    {
        this.timeRatio = timeRatio;
    }
}
