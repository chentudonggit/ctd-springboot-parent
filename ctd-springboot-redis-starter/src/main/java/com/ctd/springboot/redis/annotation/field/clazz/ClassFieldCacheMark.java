package com.ctd.springboot.redis.annotation.field.clazz;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 标志类有字段属性需要加入缓存
 *
 * @author chentudong
 * @date 2020/9/3 15:31
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ClassFieldCacheMark {

    /**
     * 缓存名称
     * @return String[]
     */
    String[] cacheNames() default {};

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
