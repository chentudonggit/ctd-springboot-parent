package com.ctd.springboot.web.registrar.globa.error.clazz;

import com.ctd.springboot.web.aspect.global.error.clazz.DefaultClassGlobalErrorAspect;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * GlobalErrorImportBeanDefinitionRegistrar
 *
 * @author chentudong
 * @date 2020/8/14 20:03
 * @since 1.0
 */
public class GlobalClassLevelErrorImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Class<?> beanClass = DefaultClassGlobalErrorAspect.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanClass);
        String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
