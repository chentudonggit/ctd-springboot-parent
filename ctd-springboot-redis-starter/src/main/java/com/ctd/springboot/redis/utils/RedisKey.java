package com.ctd.springboot.redis.utils;

import java.util.Objects;

/**
 * RedisKey
 *
 * @author chentudong
 * @date 2020/9/3 15:37
 * @since 1.0
 */
public class RedisKey {

    /**
     * 冒号
     */
    public static final String COLON = ":";
    public static final String PREFIX = "prefix";
    public static final String GLOBAL = "global";

    /**
     * 拼接key
     *
     * @param obj obj
     * @return String
     */
    public static String getKey(Object... obj) {
        if (Objects.isNull(obj)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int last = obj.length - 1;
        for (int i = 0; i < obj.length; i++) {
            sb.append(obj[i]);
            if (i < last) {
                sb.append(COLON);
            }
        }
        return sb.toString();
    }
}
