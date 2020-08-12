package com.ctd.springboot.backend.exceptions;

import com.ctd.springboot.common.core.constants.code.ResultCodeSequence;
import com.ctd.springboot.common.core.enums.code.CodeEnum;
import com.ctd.springboot.common.core.exception.InternalException;
import com.ctd.springboot.common.core.exception.UnifiedException;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.springboot.common.core.web.result.code.ResultCode;
import com.google.common.collect.ImmutableMap;
import com.netflix.client.ClientException;
import feign.RetryableException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * CatchExceptionAdvice
 *
 * @author chentudong
 * @date 2020/3/25 19:43
 * @since 1.0
 */
@ControllerAdvice
public class CatchExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionAdvice.class);
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
        builder.put(RetryableException.class, CodeEnum.SERVICE_CALL_ERROR);
        builder.put(ClientException.class, CodeEnum.SERVICE_CALL_ERROR);
        builder.put(HttpMessageNotReadableException.class, CodeEnum.MISSING_REQUEST_BODY);
    }

    /**
     * invalidToken
     *
     * @param response response
     */
    @ExceptionHandler(InvalidTokenException.class)
    public void invalidToken(HttpServletResponse response) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        try {
            response.sendError(unauthorized.value(), unauthorized.getReasonPhrase());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try (Writer writer = response.getWriter()) {
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 捕获 CustomException 类型的异常
     *
     * @param e        e
     * @param response response
     */
    @ExceptionHandler(InternalException.class)
    public void customException(InternalException e, HttpServletResponse response) {
        // 记录日志
        LOGGER.error(e.getMessage(), e);
        // 把错误信息返回给前端
        ResponseVO.responseFailure(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), getMessage(e));
    }

    /**
     * 统一弹窗提示
     *
     * @param e        e
     * @param response response
     */
    @ExceptionHandler(UnifiedException.class)
    public void unifiedException(UnifiedException e, HttpServletResponse response) {
        // 记录日志
        LOGGER.error(e.getMessage(), e);
        // 把错误信息返回给前端
        ResponseVO.responseFailure(response, ResultCodeSequence.CODE_ERROR_POPUP, getMessage(e));
    }

    /**
     * 捕获 Exception 类型的异常
     *
     * @param e        e
     * @param response response
     */
    @ExceptionHandler(Exception.class)
    public void exception(Exception e, HttpServletResponse response) {
        LOGGER.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        if (Objects.isNull(EXCEPTIONS)) {
            EXCEPTIONS = builder.build();
        }
        Throwable cause = e.getCause();
        Class c;
        if (Objects.isNull(cause)) {
            c = e.getClass();
        } else {
            c = cause.getClass();
        }
        final ResultCode resultCode = EXCEPTIONS.get(c);
        Integer code = 500;
        if (Objects.nonNull(resultCode)) {
            code = resultCode.code();
        } else {
            code = CodeEnum.SERVER_ERROR.code();
        }
        // 把错误信息返回给前端
        ResponseVO.responseFailure(response, code, getMessage(e));
    }

    /**
     * 获取异常信息
     *
     * @param e e
     * @return String
     */
    private String getMessage(Exception e) {
        AssertUtils.isNull(e, "异常类不能为空");
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
