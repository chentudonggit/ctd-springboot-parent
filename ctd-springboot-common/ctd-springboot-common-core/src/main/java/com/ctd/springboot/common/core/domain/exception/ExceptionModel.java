package com.ctd.springboot.common.core.domain.exception;

import java.util.Date;
import java.util.List;

/**
 * ExceptionModel
 *
 * @author chentudong
 * @date 2020/8/12 0:12
 * @since 1.0
 */
public class ExceptionModel {
    /**
     * 发生时间
     */
    private Date timestamp;

    /**
     * 相应状态
     */
    private Integer status;

    /**
     * 错误原因
     */
    private String error;

    /**
     * exception中包含的信息
     */
    private String message;

    /**
     * 出错的路径
     */
    private String path;

    /**
     * 抛出的异常 全称 java.lang.RuntimeException
     */
    private String throwExceptionClass;

    /**
     * 异常链
     */
    private List<ExceptionChain> exceptionChain;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThrowExceptionClass() {
        return throwExceptionClass;
    }

    public void setThrowExceptionClass(String throwExceptionClass) {
        this.throwExceptionClass = throwExceptionClass;
    }

    public List<ExceptionChain> getExceptionChain() {
        return exceptionChain;
    }

    public void setExceptionChain(List<ExceptionChain> exceptionChain) {
        this.exceptionChain = exceptionChain;
    }
}
