package com.ctd.springboot.feign.exception.exception.remote.base;

import com.ctd.springboot.common.core.domain.exception.ExceptionChain;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * BaseRemoteCallException
 *
 * @author chentudong
 * @date 2020/8/11 21:48
 * @since 1.0
 */
public abstract class BaseRemoteCallException extends RuntimeException {
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public BaseRemoteCallException() {
    }

    public BaseRemoteCallException(String message) {
        super(message);
    }

    public BaseRemoteCallException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * throwException
     *
     * @param message message
     * @return BaseRemoteCallException
     */
    public abstract BaseRemoteCallException throwException(String message);

    /**
     * throwException
     *
     * @param message         message
     * @param exceptionChains exceptionChains
     * @return BaseRemoteCallException
     */
    public abstract BaseRemoteCallException throwException(String message, @NonNull List<ExceptionChain> exceptionChains);

    /**
     * throwException
     *
     * @param message message
     * @param cause   cause
     * @return BaseRemoteCallException
     */
    public abstract BaseRemoteCallException throwException(String message, Throwable cause);


    /**
     * 判断异常是否为原始异常的子类
     *
     * @param exception exception
     * @return Boolean
     */
    public abstract Boolean isInstanceOf(Class<? extends Throwable> exception);
}
