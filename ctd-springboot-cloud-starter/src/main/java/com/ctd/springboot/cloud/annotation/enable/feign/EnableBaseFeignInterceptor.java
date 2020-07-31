package com.ctd.springboot.cloud.annotation.enable.feign;

import com.ctd.springboot.cloud.config.feign.interceptor.BaseFeignInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启feign 把自定义分装的数据传递给下游服务器
 *
 * @author chentudong
 * @date 2020/7/29 19:56
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(BaseFeignInterceptorConfig.class)
public @interface EnableBaseFeignInterceptor {
}
