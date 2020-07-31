package com.ctd.springboot.webflux.handler.json;

import com.ctd.springboot.common.core.vo.response.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 403拒绝访问异常处理，转换为JSON
 *
 * @author chentudong
 * @date 2020/3/8 14:27
 * @since 1.0
 */
public class JsonAccessDeniedHandler implements ServerAccessDeniedHandler
{
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e)
    {
        return ResponseVO.responseWriter(exchange, HttpStatus.FORBIDDEN.value(), e.getMessage());
    }
}
