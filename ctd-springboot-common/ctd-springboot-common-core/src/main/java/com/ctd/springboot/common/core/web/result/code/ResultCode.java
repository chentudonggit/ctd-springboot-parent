package com.ctd.springboot.common.core.web.result.code;

/**
 * ResultCode
 *
 * @author chentudong
 * @date 2020/3/7 11:40
 * @since 1.0
 */
public interface ResultCode
{
    /**
     * success
     *
     * @return Boolean
     */
    Boolean success();

    /**
     * code
     *
     * @return Integer
     */
    Integer code();

    /**
     * message
     *
     * @return String
     */
    String message();
}
