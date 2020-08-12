package com.ctd.springboot.feign.exception.configuration.bean;

import com.ctd.springboot.feign.exception.decoder.FeignExceptionDecoder;
import com.ctd.springboot.feign.exception.exception.remote.RemoteCallException;
import com.ctd.springboot.feign.exception.exception.remote.base.BaseRemoteCallException;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * BeanConfiguration
 *
 * @author chentudong
 * @date 2020/8/13 0:44
 * @since 1.0
 */
@Configuration
public class BeanConfiguration
{
    @Bean
    @ConditionalOnMissingBean
    public Request.Options feignOptions() {
        //修改默认超时时间
        return new Request.Options(10 * 1000, TimeUnit.MILLISECONDS,
                10 * 1000, TimeUnit.MILLISECONDS, true);
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer feignRetry() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDecoder errorDecoder(@Autowired BaseRemoteCallException baseRemoteCallException) {
        return new FeignExceptionDecoder(baseRemoteCallException);
    }

    @Bean
    @ConditionalOnMissingBean
    public BaseRemoteCallException baseRemoteCallException() {
        //默认异常处理器
        return new RemoteCallException();
    }
}
