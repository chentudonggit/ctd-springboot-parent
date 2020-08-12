package com.ctd.springboot.feign.exception.registry;

import com.ctd.springboot.feign.exception.annotation.mvc.EnableFeignWebMvcExceptionHandler;
import com.ctd.springboot.feign.exception.annotation.reactive.EnableFeignWebFluxExceptionHandler;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;

/**
 * RegistryFeignExceptionHandler
 *
 * @author chentudong
 * @date 2020/8/11 20:58
 * @since 1.0
 */
public class RegistryFeignExceptionHandler implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryFeignExceptionHandler.class);

    @Override
    public void setEnvironment(Environment environment) {

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableFeignWebMvcExceptionHandler.class.getName()));
        if(Objects.isNull(annotationAttributes)){
            annotationAttributes =AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableFeignWebFluxExceptionHandler.class.getName()));
        }
        Class<? extends ErrorDecoder> decoderClass = annotationAttributes.getClass("decoderClass");
        ErrorDecoder errorDecoder = BeanUtils.instantiateClass(decoderClass);

        AbstractBeanDefinition decoder = BeanDefinitionBuilder
                .genericBeanDefinition(ErrorDecoder.class, () -> errorDecoder)
                .setAutowireMode(AUTOWIRE_BY_TYPE)
                .getBeanDefinition();
        registry.registerBeanDefinition(decoder.getBeanClassName(), decoder);

        Class<? extends ErrorAttributes> handlerClass = annotationAttributes.getClass("handlerClass");

        ErrorAttributes errorAttributes = BeanUtils.instantiateClass(handlerClass);
        AbstractBeanDefinition handler = BeanDefinitionBuilder
                .genericBeanDefinition(ErrorAttributes.class, () -> errorAttributes)
                .setAutowireMode(AUTOWIRE_BY_TYPE)
                .getBeanDefinition();
        registry.registerBeanDefinition(handler.getBeanClassName(), handler);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("'{}' and '{}' has been successfully registered", handler.getBeanClassName(), decoder.getBeanClassName());
        }
    }
}
