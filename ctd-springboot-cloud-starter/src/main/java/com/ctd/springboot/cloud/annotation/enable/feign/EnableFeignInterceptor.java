package com.ctd.springboot.cloud.annotation.enable.feign;

import com.ctd.springboot.cloud.config.feign.interceptor.BaseFeignInterceptorConfig;
import com.ctd.springboot.cloud.config.feign.interceptor.http.FeignHttpInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableFeignInterceptor
 *
 * @author chentudong
 * @date 2020/7/30 9:02
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({BaseFeignInterceptorConfig.class, FeignHttpInterceptorConfig.class})
public @interface EnableFeignInterceptor {
}
