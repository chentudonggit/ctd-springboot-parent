package com.ctd.springboot.webflux.point.json;

import com.ctd.springboot.common.core.vo.response.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JsonAuthenticationEntryPoint
 * 401未授权异常处理，转换为JSON
 *
 * @author chentudong
 * @date 2020/3/8 14:25
 * @since 1.0
 */
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint
{
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e)
    {
        return ResponseVO.responseWriter(exchange, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}
