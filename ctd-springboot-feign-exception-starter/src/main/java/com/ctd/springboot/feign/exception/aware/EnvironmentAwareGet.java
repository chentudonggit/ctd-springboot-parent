package com.ctd.springboot.feign.exception.aware;

import com.ctd.springboot.feign.exception.context.FeignExceptionHandlerContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * EnvironmentAwareGet
 *
 * @author chentudong
 * @date 2020/8/12 22:57
 * @since 1.0
 */
public class EnvironmentAwareGet implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        FeignExceptionHandlerContext.setEnvironment(environment);
    }
}
