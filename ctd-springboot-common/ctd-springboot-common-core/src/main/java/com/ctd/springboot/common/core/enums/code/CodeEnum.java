package com.ctd.mall.framework.common.core.enums.code;

import com.ctd.mall.framework.common.core.constants.code.ResultCodeSequence;
import com.ctd.mall.framework.common.core.web.result.code.ResultCode;

/**
 * CodeEnum
 *
 * @author chentudong
 * @date 2020/3/7 11:42
 * @since 1.0
 */
public enum CodeEnum implements ResultCode
{
    /**
     * SUCCESS 操作成功
     */
    SUCCESS(ResultCodeSequence.TRUE, ResultCodeSequence.SUCCESS, "操作成功！"),

    /**
     * FAIL 操作失败
     */
    FAIL(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "操作失败！"),

    /**
     * UNAUTHENTICATED 此操作需要登陆系统
     */
    UNAUTHENTICATED(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "此操作需要登陆系统！"),

    /**
     * UNAUTHORISED 权限不足，无权操作！
     */
    UNAUTHORISED(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "权限不足，无权操作！"),

    /**
     * MISSING_REQUEST_BODY Post请求中，body中没有传输数据！
     */
    MISSING_REQUEST_BODY(ResultCodeSequence.FALSE, ResultCodeSequence.SYSTEM_ERROR, "Post请求中，body中没有传输数据！"),

    /**
     * SERVICE_CALL_ERROR 服务调用出错
     */
    SERVICE_CALL_ERROR(ResultCodeSequence.FALSE, ResultCodeSequence.COMMON + 6, "微服务调用出错！"),

    /**
     * SERVER_ERROR 抱歉，系统繁忙，请稍后重试！
     */
    SERVER_ERROR(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "抱歉，系统繁忙，请稍后重试！");

    /**
     * code
     */
    private Integer code;

    /**
     * 状态
     */
    private Boolean success;

    /**
     * 提示信息
     */
    private String message;

    CodeEnum(Boolean success, Integer code, String message)
    {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public Integer getCode()
    {
        return code;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    /**
     * success
     *
     * @return Boolean
     */
    @Override
    public Boolean success()
    {
        return success;
    }

    /**
     * code
     *
     * @return Integer
     */
    @Override
    public Integer code()
    {
        return code;
    }

    /**
     * message
     *
     * @return String
     */
    @Override
    public String message()
    {
        return message;
    }
}
