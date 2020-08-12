package com.ctd.springboot.feign.exception.annotation.reactive;

import com.ctd.springboot.feign.exception.aware.EnvironmentAwareGet;
import com.ctd.springboot.feign.exception.configuration.error.reactive.CustomErrorWebFluxAutoConfiguration;
import com.ctd.springboot.feign.exception.configuration.handler.reactive.WebFluxRequestMappingHandlerMappingConfiguration;
import com.ctd.springboot.feign.exception.configuration.reactive.WebFluxFeignConfiguration;
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
@ImportAutoConfiguration(classes = {WebFluxRequestMappingHandlerMappingConfiguration.class, WebFluxFeignConfiguration.class,
         CustomErrorWebFluxAutoConfiguration.class})
public @interface EnableFeignWebFluxExceptionHandler {

}
