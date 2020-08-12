package com.ctd.springboot.feign.exception.configuration.reactive;

import com.ctd.springboot.feign.exception.configuration.bean.BeanConfiguration;
import feign.Contract;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * todo虚位以待，请打造属于自己的品牌
 *
 * @author chentudong
 * @date 2020/8/13 0:49
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
@Import(BeanConfiguration.class)
public class WebFluxFeignConfiguration
{
    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract()
    {
        return new Contract.Default();
    }
}
