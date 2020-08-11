package com.ctd.springboot.feign.exception.handler;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.feign.exception.constant.ExceptionConstant;
import com.ctd.springboot.feign.exception.context.FeignExceptionHandlerContext;
import com.ctd.springboot.common.core.domain.exception.ExceptionChain;
import com.ctd.springboot.feign.exception.exception.remote.RemoteCallException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

/**
 * FeignExceptionHandler
 *
 * @author chentudong
 * @date 2020/8/11 23:24
 * @since 1.0
 */
public class FeignExceptionHandler extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Throwable error = super.getError(webRequest);
        //springMVC异常
        if (Objects.isNull(error)) {
            return super.getErrorAttributes(webRequest, includeStackTrace);
        }
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        List<ExceptionChain> exceptionChains = null;
        if (error instanceof RemoteCallException) {
            exceptionChains = ((RemoteCallException) error).getExceptionChains();
        } else {
            Object attribute = webRequest.getAttribute(ExceptionConstant.EXCEPTION_CHAIN_KEY, RequestAttributes.SCOPE_REQUEST);
            if (Objects.nonNull(attribute)) {
                exceptionChains = JSON.parseArray(attribute.toString(), ExceptionChain.class);
            }
        }
        if (Objects.isNull(exceptionChains)) {
            exceptionChains = new ArrayList<>(1);
        }

        ExceptionChain exceptionChain = new ExceptionChain();
        exceptionChain.setMessage(error.getMessage());
        exceptionChain.setPath(errorAttributes.get("path").toString());
        exceptionChain.setTimestamp(new Date());
        exceptionChain.setApplicationName(FeignExceptionHandlerContext.getApplicationName());
        //添加发生的异常类信息 以便下一步处理
        if (Objects.nonNull(error.getClass())) {
            exceptionChain.setExceptionClass(error.getClass().getTypeName());
        }
        exceptionChains.add(exceptionChain);
        errorAttributes.put(ExceptionConstant.EXCEPTION_CHAIN_KEY, exceptionChains);
        return errorAttributes;
    }
}
