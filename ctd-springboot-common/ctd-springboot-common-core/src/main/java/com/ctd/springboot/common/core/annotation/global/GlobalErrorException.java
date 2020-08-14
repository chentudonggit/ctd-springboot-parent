package com.ctd.springboot.common.core.annotation.global;

import java.lang.annotation.*;

/**
 * 全局异常捕获处理类
 *
 * @author chentudong
 * @date 2020/8/14 9:17
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface GlobalErrorException {
}
