package com.ctd.springboot.web.aspect.global.error.method;

import com.ctd.springboot.web.aspect.global.error.AbstractMethodGlobalErrorAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 全局异常，方法级别拦截
 *
 * @author chentudong
 * @date 2020/8/14 17:24
 * @since 1.0
 */
@Aspect
@Component
public class DefaultMethodGlobalErrorAspect extends AbstractMethodGlobalErrorAspect {

    /**
     * log
     */
    @Pointcut("@annotation(com.ctd.springboot.common.core.annotation.global.GlobalErrorException)")
    @Override
    public void log() {

    }
}
