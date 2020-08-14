package com.ctd.springboot.common.core.vo.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctd.springboot.common.core.constants.code.ResultCodeSequence;
import com.ctd.springboot.common.core.vo.page.PageVO;
import com.ctd.springboot.common.core.vo.result.ResultVO;
import com.ctd.springboot.common.core.web.result.code.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

/**
 * 响应
 *
 * @author chentudong
 * @date 2020/3/7 11:38
 * @since 1.0
 */
@ApiModel("ResponseVO")
public class ResponseVO extends LinkedHashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = -6870182069212716606L;

    /**
     * KEY_DATA
     *
     * @since 1.0
     */
    @ApiModelProperty("data")
    private static final String KEY_DATA = "data";
    /**
     * KEY_CODE
     *
     * @since 1.0
     */
    @ApiModelProperty("code")
    public static final String KEY_CODE = "code";
    /**
     * KEY_MESSAGE
     *
     * @since 1.0
     */
    @ApiModelProperty("message")
    public static final String KEY_MESSAGE = "message";
    /**
     * KEY_SUCCESS
     *
     * @since 1.0
     */
    @ApiModelProperty("成功/失败")
    private static final String KEY_SUCCESS = "success";
    /**
     * 总页码
     *
     * @since 1.0
     */
    @ApiModelProperty("总页码")
    private static final String KEY_TOTAL_PAGE = "total_page";
    /**
     * 总记录数
     *
     * @since 1.0
     */
    @ApiModelProperty("总记录数")
    private static final String KEY_TOTAL_COUNT = "total_count";
    /**
     * 当前页码
     *
     * @since 1.0
     */
    @ApiModelProperty("当前页码")
    private static final String KEY_PAGE = "page";
    /**
     * 每页显示多少条数据
     *
     * @since 1.0
     */
    @ApiModelProperty("每页显示多少条数据")
    private static final String KEY_SIZE = "size";

    /**
     * responseWriter
     *
     * @param objectMapper objectMapper
     * @param response     response
     * @param msg          msg
     * @param httpStatus
     * @throws IOException IOException
     */
    public static void responseWriter(ObjectMapper objectMapper, HttpServletResponse response, String msg, int httpStatus) throws IOException {
        responseWrite(objectMapper, response, ResultVO.succeedWith(null, httpStatus, msg));
    }

    /**
     * responseWrite
     *
     * @param objectMapper objectMapper
     * @param response     response
     * @param result       result
     * @throws IOException IOException
     */
    public static void responseWrite(ObjectMapper objectMapper, HttpServletResponse response, ResultVO<Object> result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (Writer writer = getWriter(response.getOutputStream())) {
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        }
    }

    /**
     * 成功
     * webflux的response返回json对象
     *
     * @param exchange   exchange
     * @param httpStatus httpStatus
     * @param msg        msg
     * @return Mono<Void>
     */
    public static Mono<Void> responseWriter(ServerWebExchange exchange, int httpStatus, String msg) {
        return responseWriter(exchange, ResultVO.succeedWith(null, httpStatus, msg), HttpStatus.valueOf(httpStatus));
    }

    /**
     * 失败
     * webflux的response返回json对象
     *
     * @param exchange   exchange
     * @param httpStatus httpStatus
     * @param msg        msg
     * @return Mono<Void>
     */
    public static Mono<Void> responseFailureWriter(ServerWebExchange exchange, int httpStatus, String msg) {
        return responseWriter(exchange, ResultVO.failedWith(null, httpStatus, msg), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * responseNotLogin
     *
     * @param exchange exchange
     * @param msg      msg
     * @return Mono<Void>
     */
    public static Mono<Void> responseNotLogin(ServerWebExchange exchange, String msg) {
        return responseWriter(exchange, ResultVO.failedWith(null, HttpStatus.UNAUTHORIZED.value(), msg), HttpStatus.UNAUTHORIZED);
    }

    /**
     * responseWriter
     *
     * @param exchange   exchange
     * @param result     result
     * @param httpStatus httpStatus
     * @return Mono<Void>
     */
    public static Mono<Void> responseWriter(ServerWebExchange exchange, ResultVO result, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(httpStatus);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }

    /**
     * Response
     *
     * @param resultCode resultCode
     */
    public ResponseVO(ResultCode resultCode) {
        this.put(KEY_CODE, resultCode.code());
        this.put(KEY_MESSAGE, resultCode.message());
        this.put(KEY_SUCCESS, resultCode.success());
    }

    /**
     * Response
     *
     * @param code       code
     * @param successful successful
     * @param message    message
     */
    public ResponseVO(Integer code, Boolean successful, String message) {
        this.put(KEY_CODE, code);
        this.put(KEY_MESSAGE, message);
        this.put(KEY_SUCCESS, successful);
    }

    /**
     * code
     *
     * @param resultCode resultCode
     * @return ResponseVO
     * @since 1.0
     */
    public static ResponseVO code(ResultCode resultCode) {
        return new ResponseVO(resultCode);
    }

    /**
     * data
     *
     * @param data data
     * @return ResponseVO
     * @since 1.0
     */
    public static ResponseVO data(Object data) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseVO responseResult = new ResponseVO(httpStatus.value(), true, httpStatus.getReasonPhrase());
        responseResult.put(KEY_DATA, data);
        return responseResult;
    }

    /**
     * Response
     *
     * @param page page
     * @return ResponseVO
     * @since 1.0
     */
    public static ResponseVO page(PageVO<?> page) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseVO responseResult = new ResponseVO(httpStatus.value(), true,
                httpStatus.getReasonPhrase());
        //分页数据
        responseResult.put(KEY_DATA, page.getData());
        responseResult.put(KEY_TOTAL_PAGE, page.getTotalPage());
        responseResult.put(KEY_TOTAL_COUNT, page.getTotalCount());
        responseResult.put(KEY_PAGE, page.getPage());
        responseResult.put(KEY_SIZE, page.getSize());
        return responseResult;
    }

    /**
     * 一般用于 对数据的，增删改
     * 增删改有可能会失败
     *
     * @param row 改变数据库的记录数量
     * @return ResponseVO
     * @since 1.0
     */
    public static ResponseVO row(long row) {
        HttpStatus httpStatus = HttpStatus.OK;
        boolean isSuccessful = true;
        if (row == 0L) {
            isSuccessful = false;
            httpStatus = HttpStatus.NOT_MODIFIED;
        }
        ResponseVO responseResult = new ResponseVO(httpStatus.value(), isSuccessful,
                httpStatus.getReasonPhrase());
        responseResult.put(KEY_DATA, row);
        return responseResult;
    }

    /**
     * 输出流
     *
     * @param objectMapper objectMapper
     * @param response     response
     * @param obj          obj
     * @throws IOException IOException
     */
    public static void responseSucceed(ObjectMapper objectMapper, HttpServletResponse response, Object obj) throws IOException {
        responseWrite(objectMapper, response, ResultVO.succeed(obj));
    }

    /**
     * responseFailure
     *
     * @param response response
     * @param code     code
     * @param message  message
     */
    public static void responseFailure(HttpServletResponse response, Integer code, String message) {
        response.setStatus(ResultCodeSequence.SYSTEM_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            try (Writer writer = getWriter(response.getOutputStream())) {
                writer.write(JSON.toJSONString(new ResponseVO(code, false, message)));
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getWriter
     *
     * @param outputStream outputStream
     * @return Writer
     */
    public static Writer getWriter(ServletOutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    /**
     * successfulMessage
     *
     * @param message message
     * @return ResponseVO
     * @since 1.0
     */
    public ResponseVO successfulMessage(String message) {
        boolean isSuccessful = (Boolean) this.get(KEY_SUCCESS);
        if (isSuccessful) {
            this.put(KEY_MESSAGE, message);
        }
        return this;
    }

    /**
     * errorMessage
     *
     * @param message message
     * @return ResponseVO
     * @since 1.0
     */
    public ResponseVO errorMessage(String message) {
        boolean isSuccessful = (Boolean) this.get(KEY_SUCCESS);
        if (!isSuccessful) {
            this.put(KEY_MESSAGE, message);
        }
        return this;
    }

    /**
     * 报错信息
     *
     * @param message message
     * @return ResponseVO
     * @since 1.0
     */
    public ResponseVO failure(String message) {
        this.put(KEY_SUCCESS, false);
        this.put(KEY_CODE, HttpStatus.BAD_GATEWAY);
        this.put(KEY_MESSAGE, message);
        return this;
    }

    /**
     * 报错信息
     *
     * @param code    code
     * @param message message
     * @return ResponseVO
     */
    public ResponseVO failure(Integer code, String message) {
        this.put(KEY_SUCCESS, false);
        this.put(KEY_CODE, code);
        this.put(KEY_MESSAGE, message);
        return this;
    }

    /**
     * addAttribute
     *
     * @param key   key
     * @param value value
     * @return ResponseVO
     * @since 1.0
     */
    public ResponseVO addAttribute(String key, Object value) {
        this.put(key, value);
        return this;
    }
}
