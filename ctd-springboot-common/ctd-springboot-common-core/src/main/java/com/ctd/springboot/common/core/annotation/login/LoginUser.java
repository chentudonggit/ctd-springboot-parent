package com.ctd.springboot.common.core.annotation.login;

import java.lang.annotation.*;

/**
 * LoginUser
 *
 * @author chentudong
 * @date 2020/3/7 11:35
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser
{
    /**
     * 所有信息
     *
     * @return boolean
     */
    boolean allInfo() default false;
}
