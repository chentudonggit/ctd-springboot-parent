package com.ctd.springboot.feign.exception.configuration.error.reactive;


import com.ctd.springboot.feign.exception.handler.reactive.FeignReactiveExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * CustomErrorWebFluxAutoConfiguration
 *
 * @author chentudong
 * @date 2020/8/12 20:27
 * @since 1.0
 */
@Configuration
@AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)
public class CustomErrorWebFluxAutoConfiguration {
    @Primary
    @Bean
    @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
    public DefaultErrorAttributes errorAttributes() {
        return new FeignReactiveExceptionHandler();
    }
}
