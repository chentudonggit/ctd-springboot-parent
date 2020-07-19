package com.ctd.springboot.common.core.constants.code;

/**
 * ResultCodeSequence
 *
 * @author chentudong
 * @date 2020/3/7 11:44
 * @since 1.0
 */
public interface ResultCodeSequence {
    /**
     * 成功
     */
    Integer SUCCESS = 200;

    /**
     * 系统异常码
     */
    Integer SYSTEM_ERROR = 500;

    /**
     * 统一异常码
     */
    Integer COMMON = 10000;

    /**
     * 统一弹窗错误
     */
    Integer CODE_ERROR_POPUP = 1002;

    /**
     * TRUE
     */
    Boolean TRUE = true;

    /**
     * FALSE
     */
    Boolean FALSE = false;
}
