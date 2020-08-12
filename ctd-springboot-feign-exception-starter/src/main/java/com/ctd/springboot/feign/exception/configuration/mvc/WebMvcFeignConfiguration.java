package com.ctd.springboot.feign.exception.configuration.mvc;

import com.ctd.springboot.feign.exception.configuration.bean.BeanConfiguration;
import feign.Contract;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FeignConfiguration
 *
 * @author chentudong
 * @date 2020/8/11 21:46
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Import(BeanConfiguration.class)
public class WebMvcFeignConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract() {
        return new SpringMvcContract();
    }
}
