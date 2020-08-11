package com.ctd.springboot.feign.exception.configuration.handler;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * RequestMappingHandlerMappingConfiguration
 *
 * @author chentudong
 * @date 2020/8/11 21:43
 * @since 1.0
 */
@Configuration
public class RequestMappingHandlerMappingConfiguration  implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new FeignRequestMappingHandlerMapping();
    }

    private static class FeignRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        @Override
        protected boolean isHandler(Class<?> beanType) {
            //1 父类处理器 2 不能包含feignClient 3 不能是接口
            return super.isHandler(beanType)
                    && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class)
                    && !beanType.isInterface();
        }
    }
}
