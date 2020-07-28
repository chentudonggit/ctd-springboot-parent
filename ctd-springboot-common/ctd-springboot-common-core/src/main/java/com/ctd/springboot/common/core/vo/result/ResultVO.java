package com.ctd.springboot.common.core.vo.result;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.common.core.enums.code.CodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * ResultVO
 *
 * @author chentudong
 * @date 2020/3/7 11:41
 * @since 1.0
 */
@ApiModel("ResultVO")
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 5277095580059488244L;

    /**
     * data
     */
    @ApiModelProperty("data")
    private T data;

    /**
     * code
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * message
     */
    @ApiModelProperty("提示信息")
    private String message;

    /**
     * 成功/失败
     */
    @ApiModelProperty("success")
    private Boolean success;

    public ResultVO() {
    }

    public ResultVO(T data, Integer code, String message, Boolean success) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static <T> ResultVO<T> succeed() {
        return succeedWith(null, CodeEnum.SUCCESS.code(), CodeEnum.SUCCESS.message());
    }

    public static <T> ResultVO<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResultVO<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResultVO<T> succeed(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.code(), CodeEnum.SUCCESS.message());
    }

    public static <T> ResultVO<T> succeedWith(T data, Integer code, String msg) {
        return new ResultVO<>(data, code, msg, true);
    }

    public static <T> ResultVO<T> failed(String msg) {
        return failedWith(null, CodeEnum.SERVER_ERROR.getCode(), msg);
    }

    public static <T> ResultVO<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.SERVER_ERROR.getCode(), msg);
    }

    public static <T> ResultVO<T> failedWith(T data, Integer code, String msg) {
        return new ResultVO<>(data, code, msg, false);
    }

    public static <T> ResultVO<T> failedWith(Integer code, String msg) {
        return new ResultVO<>(null, code, msg, false);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
