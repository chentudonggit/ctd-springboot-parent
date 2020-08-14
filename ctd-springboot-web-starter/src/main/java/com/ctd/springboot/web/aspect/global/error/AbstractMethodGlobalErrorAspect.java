package com.ctd.springboot.web.aspect.global.error;

import com.ctd.springboot.common.core.annotation.global.GlobalErrorException;
import com.ctd.springboot.web.common.CommonException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 全局异常拦截
 *
 * @author chentudong
 * @date 2020/8/14 9:23
 * @since 1.0
 */
public abstract class AbstractMethodGlobalErrorAspect {

    /**
     * log
     */
    public abstract void log();

    /**
     * doAfterThrowing
     *
     * @param e e
     */
    @AfterThrowing(throwing = "e", pointcut = "log() && @annotation(globalError)")
    public void doAfterThrowing(Throwable e, GlobalErrorException globalError) {
        CommonException.exception(e, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse());
    }

}
