package com.ctd.springboot.web.aspect.global.error.clazz;

import com.ctd.springboot.web.aspect.global.error.AbstractClassGlobalErrorAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 类上注解，拦截全局异常
 *
 * @author chentudong
 * @date 2020/8/14 17:11
 * @since 1.0
 */
@Aspect
@Component
public class DefaultClassGlobalErrorAspect extends AbstractClassGlobalErrorAspect {
    /**
     * log
     */
    @Pointcut("@within(com.ctd.springboot.common.core.annotation.global.GlobalErrorException)")
    @Override
    public void log() {

    }
}
