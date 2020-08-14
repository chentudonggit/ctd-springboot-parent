package com.ctd.springboot.cloud.exception.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctd.springboot.common.core.domain.exception.ExceptionChain;
import com.ctd.springboot.common.core.domain.exception.ExceptionModel;
import com.ctd.springboot.common.core.exception.InternalException;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 微服务异常传递，多个微服务调用时：
 * A 调 B ，B 调 C 当 C 抛出异常时，传递给B ，B再传递给A，
 * 保证异常信息不会丢失，通知调用方，报错明细，让调用方处理是否提示用户
 *
 * @author chentudong
 * @date 2020/3/7 22:06
 * @since 1.0
 */
public class ErrorDecoderException implements ErrorDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorDecoderException.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            Response.Body body = response.body();
            if (Objects.isNull(body)) {
                return new InternalException("处理失败.");
            }
            // 获取原始的返回内容
            String json = Util.toString(body.asReader(StandardCharsets.UTF_8));
            ExceptionModel exceptionModel = toExceptionModel(json);
            String classPath = null;
            String message = null;
            if (Objects.nonNull(exceptionModel)) {
                classPath = exceptionModel.getThrowExceptionClass();
                message = exceptionModel.getMessage();
            }
            if (StringUtils.isBlank(classPath)) {
                JSONObject responseJson = JSON.parseObject(json);
                classPath = responseJson.getString("exception");
                message = responseJson.getString("message");
                if (StringUtils.isBlank(classPath)) {
                    String error = responseJson.getString("error");
                    if (StringUtils.isNotBlank(error)) {
                        Exception exception = new InternalException("path:" + responseJson.getString("path")
                                + "  error:" + responseJson.getString("error")
                                + "  status:" + responseJson.getString("status")
                                + "  message:" + responseJson.getString("message"));
                        LOGGER.error("catch exception : {}\r\nexception: ", json, exception);
                        return exception;
                    }
                }
            }
            // 取得Class对象
            Class<?> cls = Class.forName(classPath);
            Constructor<Exception> con = (Constructor<Exception>) cls.getConstructor(String.class);
            Exception exception = con.newInstance(message);
            LOGGER.error("catch exception : {}\r\nexception: ", json, exception);
            return exception;
        } catch (Exception e) {
            return new InternalException(e.getMessage());
        }
    }

    private ExceptionModel toExceptionModel(String json) {
        if (StringUtils.isNotBlank(json)) {
            try {
                ExceptionModel exceptionModel = JSON.parseObject(json, ExceptionModel.class);
                if (Objects.nonNull(exceptionModel)) {
                    exceptionModel.setThrowExceptionClass(getFirstThrowExceptionClass(exceptionModel.getExceptionChain()));
                }
                return exceptionModel;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getFirstThrowExceptionClass(List<ExceptionChain> exceptionChains) {
        if (AssertUtils.nonNull(exceptionChains) && !exceptionChains.isEmpty()) {
            LOGGER.error("exceptionChains = {}", JSON.toJSONString(exceptionChains));
            return exceptionChains.get(0).getExceptionClass();
        }
        return null;
    }
}
