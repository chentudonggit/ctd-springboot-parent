package com.ctd.springboot.cloud.config.feign;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ctd.springboot.cloud.exception.service.ErrorDecoderException;
import com.ctd.springboot.cloud.format.date.DateFormatter;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;

/**
 * FeignConfigRegistrar
 *
 * @author chentudong
 * @date 2020/3/7 22:04
 * @since 1.0
 */
@Configuration
@Order(Integer.MIN_VALUE)
public class FeignConfigRegistrar implements FeignFormatterRegistrar {
    /**
     * 注入Bean : HttpMessageConverters，以支持fastjson
     *
     * @return HttpMessageConverters
     */
    @Primary
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fastConvert.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConvert);
    }

    /**
     * 异常
     *
     * @return ErrorDecoder
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoderException();
    }

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }
}
