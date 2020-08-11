package com.ctd.springboot.common.core.domain.exception;

import java.util.Date;

/**
 * ExceptionChain
 *
 * @author chentudong
 * @date 2020/8/11 21:50
 * @since 1.0
 */
public class ExceptionChain {

    /**
     * happened timestamp
     */
    private Date timestamp;

    /**
     * happened exceptionClass
     */
    private String exceptionClass;

    /**
     * message of exception
     */
    private String message;

    /**
     * the feign client path url
     */
    private String path;

    /**
     *
     */
    private String applicationName;


    public boolean isAssignableFrom(Class<? extends Throwable> exception) {
        try {
            return exception.isAssignableFrom(Class.forName(exceptionClass));
        } catch (Exception e) {
            return false;
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
