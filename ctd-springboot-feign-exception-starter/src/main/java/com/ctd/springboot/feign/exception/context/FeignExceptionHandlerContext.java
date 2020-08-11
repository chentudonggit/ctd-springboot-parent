package com.ctd.springboot.feign.exception.context;

import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * FeignExceptionHandlerContext
 *
 * @author chentudong
 * @date 2020/8/11 21:07
 * @since 1.0
 */
public class FeignExceptionHandlerContext {
    private static Environment ENVIRONMENT;

    public static String getApplicationName() {
        return Objects.isNull(ENVIRONMENT) ? "unknownServer" : ENVIRONMENT.getProperty("spring.application.name");
    }

    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    public static void setEnvironment(Environment environment) {
        if (Objects.isNull(ENVIRONMENT)) {
            FeignExceptionHandlerContext.ENVIRONMENT = environment;
        }
    }
}
