package com.ctd.springboot.feign.exception.decoder;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.common.core.domain.exception.ExceptionModel;
import com.ctd.springboot.feign.exception.exception.remote.base.BaseRemoteCallException;
import feign.Feign;
import feign.Response;
import feign.RetryableException;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * FeignExceptionDecoder
 *
 * @author chentudong
 * @date 2020/8/12 0:09
 * @since 1.0
 */
public class FeignExceptionDecoder implements ErrorDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignExceptionDecoder.class);
    private final BaseRemoteCallException baseRemoteCallException;

    public FeignExceptionDecoder(BaseRemoteCallException baseRemoteCallException) {
        this.baseRemoteCallException = baseRemoteCallException;
    }

    /**
     * Implement this method in order to decode an HTTP {@link Response} when
     * {@link Response#status()} is not in the 2xx range. Please raise application-specific exceptions
     * where possible. If your exception is retryable, wrap or subclass {@link RetryableException}
     *
     * @param methodKey {@link Feign#configKey} of the java method that invoked the request. ex.
     *                  {@code IAM#getUser()}
     * @param response  HTTP response where {@link Response#status() status} is greater than or equal
     *                  to {@code 300}.
     * @return Exception IOException, if there was a network error reading the response or an
     * application-specific exception decoded by the implementation. If the throwable is
     * retryable, it should be wrapped, or a subtype of {@link RetryableException}
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ExceptionModel exceptionModel = JSON.parseObject(Util.toString(response.body().asReader(StandardCharsets.UTF_8)), ExceptionModel.class);
            return baseRemoteCallException.throwException(exceptionModel.getMessage(), exceptionModel.getExceptionChain());
        } catch (Exception e) {
            LOGGER.error("{} has an unknown exception.", methodKey, e);
            return baseRemoteCallException.throwException("unKnowException", e);
        }
    }
}
