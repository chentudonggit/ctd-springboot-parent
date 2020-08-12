package com.ctd.springboot.feign.exception.annotation.mvc;

import com.ctd.springboot.feign.exception.aware.EnvironmentAwareGet;
import com.ctd.springboot.feign.exception.configuration.FeignConfiguration;
import com.ctd.springboot.feign.exception.configuration.error.mvc.CustomErrorMvcAutoConfiguration;
import com.ctd.springboot.feign.exception.configuration.handler.mvc.WebMvcRequestMappingHandlerMappingConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

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
@Import({EnvironmentAwareGet.class})
@ImportAutoConfiguration(classes = {WebMvcRequestMappingHandlerMappingConfiguration.class, FeignConfiguration.class,
         CustomErrorMvcAutoConfiguration.class})
public @interface EnableFeignWebMvcExceptionHandler {

}
