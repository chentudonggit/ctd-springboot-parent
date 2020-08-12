package com.ctd.springboot.feign.exception.configuration.error.mvc;

import com.ctd.springboot.feign.exception.handler.mvc.FeignMvcExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OverriderErrorMvcAutoConfiguration
 *
 * @author chentudong
 * @date 2020/8/11 23:22
 * @since 1.0
 */
@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class CustomErrorMvcAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
    public DefaultErrorAttributes errorAttributes() {
        return new FeignMvcExceptionHandler();
    }
}
