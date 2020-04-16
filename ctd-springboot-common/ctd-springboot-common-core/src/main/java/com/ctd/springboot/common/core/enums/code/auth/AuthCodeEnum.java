package com.ctd.mall.framework.common.core.enums.code.auth;

import com.ctd.mall.framework.common.core.constants.code.ResultCodeSequence;
import com.ctd.mall.framework.common.core.web.result.code.ResultCode;
import com.google.common.collect.ImmutableMap;

import java.util.Objects;

/**
 * AuthCodeEnum
 *
 * @author chentudong
 * @date 2020/3/7 11:49
 * @since 1.0
 */
public enum AuthCodeEnum implements ResultCode
{
    /**
     * AUTH_USERNAME_NONE 请输入账号
     *
     * @since 1.0
     */
    AUTH_USERNAME_NONE(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "请输入账号！"),

    /**
     * AUTH_PASSWORD_NONE 请输入密码
     *
     * @since 1.0
     */
    AUTH_PASSWORD_NONE(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "请输入密码！"),

    /**
     * AUTH_VERIFY_CODE_NONE 请输入验证码
     *
     * @since 1.0
     */
    AUTH_VERIFY_CODE_NONE(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "请输入验证码！"),

    /**
     * AUTH_ACCOUNT_NOT_EXISTS 账号不存在
     *
     * @since 1.0
     */
    AUTH_ACCOUNT_NOT_EXISTS(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "账号不存在！"),

    /**
     * AUTH_CREDENTIAL_ERROR 账号或密码错误
     *
     * @since 1.0
     */
    AUTH_CREDENTIAL_ERROR(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "账号或密码错误！"),

    /**
     * AUTH_LOGIN_ERROR 登陆过程出现异常请尝试重新操作
     *
     * @since 1.0
     */
    AUTH_LOGIN_ERROR(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP + 1, "登陆过程出现异常请尝试重新操作！"),

    /**
     * AUTH_LOGIN_APPLY_TOKEN_FAIL 申请令牌失败
     *
     * @since 1.0
     */
    AUTH_LOGIN_APPLY_TOKEN_FAIL(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP + 2, "申请令牌失败！"),

    /**
     * AUTH_LOGIN_TOKEN_SAVE_FAIL  存储令牌失败
     *
     * @since 1.0
     */
    AUTH_LOGIN_TOKEN_SAVE_FAIL(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP + 3, "存储令牌失败！"),

    /**
     * AUTH_LOGOUT_FAIL 退出失败
     *
     * @since 1.0
     */
    AUTH_LOGOUT_FAIL(ResultCodeSequence.FALSE, ResultCodeSequence.CODE_ERROR_POPUP, "退出失败！");

    /**
     * 操作代码
     */
    private Boolean success;

    /**
     * 操作代码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    private static final ImmutableMap<Integer, AuthCodeEnum> CACHE;

    static
    {
        final ImmutableMap.Builder<Integer, AuthCodeEnum> builder = ImmutableMap.builder();
        for (AuthCodeEnum authCodeEnum : values())
        {
            Integer authCode = authCodeEnum.code();
            if (Objects.nonNull(authCode))
            {
                builder.put(authCode, authCodeEnum);
            }
        }
        CACHE = builder.build();
    }

    AuthCodeEnum(boolean success, Integer code, String message)
    {
        this.success = success;
        this.code = code;
        this.message = message;
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
