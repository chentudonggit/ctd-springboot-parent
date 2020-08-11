package com.ctd.springboot.feign.exception.annotation;

import com.ctd.springboot.feign.exception.configuration.FeignConfiguration;
import com.ctd.springboot.feign.exception.configuration.error.OverriderErrorMvcAutoConfiguration;
import com.ctd.springboot.feign.exception.configuration.handler.RequestMappingHandlerMappingConfiguration;
import com.ctd.springboot.feign.exception.context.FeignExceptionHandlerContext;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.lang.annotation.*;

/**
 * EnableFeignExceptionHandler
 *
 * @author chentudong
 * @date 2020/8/11 20:57
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({EnableFeignExceptionHandler.EnvironmentAwareGet.class})
@ImportAutoConfiguration(classes = {RequestMappingHandlerMappingConfiguration.class, FeignConfiguration.class, OverriderErrorMvcAutoConfiguration.class})
public @interface EnableFeignExceptionHandler {
    class EnvironmentAwareGet implements EnvironmentAware {

        @Override
        public void setEnvironment(Environment environment) {
            FeignExceptionHandlerContext.setEnvironment(environment);
        }
    }
}
