package com.ctd.springboot.web.aspect.global.error;

import com.ctd.springboot.common.core.enums.code.CodeEnum;
import com.ctd.springboot.common.core.exception.InternalException;
import com.ctd.springboot.common.core.exception.UnifiedException;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.springboot.common.core.web.result.code.ResultCode;
import com.google.common.collect.ImmutableMap;
import com.netflix.client.ClientException;
import feign.RetryableException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 全局异常拦截
 *
 * @author chentudong
 * @date 2020/8/14 9:23
 * @since 1.0
 */
public abstract class AbstractGlobalErrorAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGlobalErrorAspect.class);
    private static final String MESSAGE = "message:";

    /**
     * 使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    /**
     * 使用builder来构建一个异常类型和错误代码的异常
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        //在这里加入一些基础的异常类型判断
        builder.put(RetryableException.class, CodeEnum.SERVER_ERROR);
        builder.put(ClientException.class, CodeEnum.SERVER_ERROR);
        builder.put(InternalException.class, CodeEnum.MISSING_REQUEST_BODY);
        builder.put(HttpMessageNotReadableException.class, CodeEnum.MISSING_REQUEST_BODY);
    }


    /**
     * log
     */
    public abstract void log();

    /**
     * doAfterThrowing
     *
     * @param e e
     */
    @AfterThrowing(throwing = "e", pointcut = "log()")
    public void doAfterThrowing(Throwable e) {
        exception(e, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse());
    }

    /**
     * exception
     *
     * @param e        e
     * @param response response
     */
    private void exception(Throwable e, HttpServletResponse response) {
        LOGGER.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        int code = 500;
        String message = getMessage(e);
        if (e instanceof UnifiedException) {
            code = 1002;
        } else if (e instanceof InternalException) {
            code = 1001;
        } else {
            EXCEPTIONS = Objects.isNull(EXCEPTIONS) ? builder.build() : EXCEPTIONS;
            Throwable cause = e.getCause();
            Class<?> c;
            if (Objects.isNull(cause)) {
                c = e.getClass();
            } else {
                c = cause.getClass();
            }
            final ResultCode resultCode = EXCEPTIONS.get(c);
            if (Objects.nonNull(resultCode)) {
                code = resultCode.code();
                message = resultCode.message();
            }
        }
        // 把错误信息返回给前端
        ResponseVO.responseFailure(response, code, message);
    }

    /**
     * 获取异常信息
     *
     * @param e e
     * @return String
     */
    private String getMessage(Throwable e) {
        // 把错误信息返回给前端
        String message = e.getMessage();
        if (StringUtils.isNoneBlank(message)) {
            if (message.contains(MESSAGE)) {
                message = message.substring(message.lastIndexOf(MESSAGE)).replace(MESSAGE, "");
            }
        } else {
            message = "未知错误！";
        }
        return message;
    }
}
