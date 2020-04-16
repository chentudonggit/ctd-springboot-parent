package com.ctd.mall.framework.common.core.utils.param;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * ParamUtils
 *
 * @author chentudong
 * @date 2020/3/7 17:08
 * @since 1.0
 */
public final class ParamUtils
{
    private ParamUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    public static final String MOBILE = "mobile";
    public static final String CODE = "code";

    /**
     * 获取参数
     *
     * @param map map
     * @param key key
     * @param <T> <T>
     * @return T
     */
    public static <T> T getParam(Map map, String key)
    {
        if (AssertUtils.nonNull(map) && StringUtils.isNotBlank(key))
        {
            return getParam(map.get(key));
        }
        return null;
    }

    /**
     * getParam
     *
     * @param obj obj
     * @param <T> <T>
     * @return T
     */
    public static <T> T getParam(Object obj)
    {
        if (AssertUtils.nonNull(obj) && StringUtils.isNotBlank(obj.toString()))
        {
            return (T) obj;
        }
        return null;
    }

    /**
     * 判断是否输入新值
     *
     * @param param    param
     * @param newParam newParam
     * @param <T>      <T>
     * @return T
     */
    public static <T> T returnParam(T param, T newParam)
    {
        if (Objects.nonNull(newParam))
        {
            return newParam;
        } else
        {
            return param;
        }
    }
}
