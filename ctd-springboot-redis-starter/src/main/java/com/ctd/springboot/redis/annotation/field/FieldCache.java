package com.ctd.springboot.redis.annotation.field;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 属性加入缓存
 *
 * @author chentudong
 * @date 2020/9/3 15:30
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface FieldCache {

    /**
     * 缓存key
     *
     * @return String
     */
    String cacheKey();

    /**
     * 是否过期
     *
     * @return boolean
     */
    boolean isExpired() default true;

    /**
     * 过期时间
     *
     * @return long
     */
    long seconds() default 60;
}
