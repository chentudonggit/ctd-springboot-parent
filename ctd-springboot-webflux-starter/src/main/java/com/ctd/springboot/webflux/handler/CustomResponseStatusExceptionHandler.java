package com.ctd.springboot.webflux.handler;

import com.ctd.springboot.common.core.enums.code.CodeEnum;
import org.springframework.cloud.gateway.support.NotFoundException;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

/**
 * CustomResponseStatusExceptionHandler
 *
 * @author chentudong
 * @date 2020/7/19 20:46
 * @since 1.0
 */
@Configuration
@Order(-1)
public class CustomResponseStatusExceptionHandler extends ResponseStatusExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseStatusExceptionHandler.class);

    /**
     * Handle the given exception. A completion signal through the return value
     * indicates error handling is complete while an error signal indicates the
     * exception is still not handled.
     *
     * @param exchange the current exchange
     * @param ex       the exception to handle
     * @return {@code Mono<Void>} to indicate when exception handling is complete
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // header set
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }
        LOGGER.error(formatError(ex, exchange.getRequest()));
        if (ex instanceof InvalidTokenException) {
            HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
            return ResponseVO.responseWriter(exchange, unauthorized.value(), ex.getMessage());
        }
        if (ex instanceof NotFoundException) {
            LOGGER.error("未发现服务");
            return serverError(exchange);
        }
        if (ex instanceof ConnectException) {
            LOGGER.error("服务准备中");
            return serverError(exchange);
        }
        return super.handle(exchange, ex);
    }

    /**
     * 格式化错误信息
     *
     * @param ex      ex
     * @param request request
     * @return String
     */
    private String formatError(Throwable ex, ServerHttpRequest request) {
        String reason = ex.getClass().getSimpleName() + ": " + ex.getMessage();
        String path = request.getURI().getRawPath();
        return "Resolved [" + reason + "] for HTTP " + request.getMethod() + " " + path;
    }

    private Mono<Void> serverError(ServerWebExchange exchange) {
        CodeEnum serverError = CodeEnum.SERVER_ERROR;
        return ResponseVO.responseFailureWriter(exchange, serverError.code(), serverError.message());
    }
}
