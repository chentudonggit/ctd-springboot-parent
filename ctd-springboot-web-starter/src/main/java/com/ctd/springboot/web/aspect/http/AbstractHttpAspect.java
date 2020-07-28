package com.ctd.springboot.web.aspect.http;

import com.ctd.springboot.common.core.request.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

/**
 * AbstractHttpAspect
 *
 * @author chentudong
 * @date 2020/7/28 18:35
 * @since 1.0
 */
public abstract class AbstractHttpAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpAspect.class);

    /**
     * log
     */
    public abstract void log();

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        LOGGER.warn("-------------- http request start --------------");
        LOGGER.warn("url = {}", RequestContext.getURL());
        LOGGER.warn("method = {}", RequestContext.getMethod());
        LOGGER.warn("ip = {}", RequestContext.getIpAddress());
        LOGGER.warn("class = {}", joinPoint.getTarget().getClass().getName());
        LOGGER.warn("class_method = {}", joinPoint.getSignature().getName());
        LOGGER.warn("args = {}", Arrays.asList(joinPoint.getArgs()));
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        long endTime = System.currentTimeMillis();
        LOGGER.warn("cost = {}ms", endTime - startTime);
        return object;
    }

    @After("log()")
    public void doAfter() {
        LOGGER.warn("start return .....");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        if (Objects.nonNull(object)) {
            LOGGER.warn("response = {}", object.toString());
        }
        LOGGER.warn("-------------- http request end --------------");
    }

    @AfterThrowing(throwing = "ex", pointcut = "log()")
    public void doAfterThrow(Throwable ex) {
        LOGGER.error(" ex =  ", ex);
        LOGGER.warn("-------------- http request error --------------");
    }
}
