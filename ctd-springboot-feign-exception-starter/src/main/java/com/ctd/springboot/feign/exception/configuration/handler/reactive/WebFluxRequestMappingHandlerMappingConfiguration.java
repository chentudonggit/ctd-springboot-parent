package com.ctd.springboot.feign.exception.configuration.handler.reactive;

import org.springframework.boot.autoconfigure.web.reactive.WebFluxRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

/**
 * WebFluxRequestMappingHandlerMappingConfiguration
 *
 * @author chentudong
 * @date 2020/8/13 0:22
 * @since 1.0
 */
public class WebFluxRequestMappingHandlerMappingConfiguration implements WebFluxRegistrations
{
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping()
    {
        return new FeignRequestMappingHandlerMapping();
    }

    private static class FeignRequestMappingHandlerMapping extends RequestMappingHandlerMapping
    {
        @Override
        protected boolean isHandler(Class<?> beanType)
        {
            //1 父类处理器 2 不能包含feignClient 3 不能是接口
            return super.isHandler(beanType)
                    && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class)
                    && !beanType.isInterface();
        }
    }
}
