package com.ctd.springboot.auth.properties.validate;

/**
 * 验证码
 *
 * @author chentudong
 * @date 2020/3/7 15:36
 * @since 1.0
 */
public class ValidateCodeProperties
{
    /**
     * 无需验证码的 clientId
     */
    private String[] ignoreClientCode = {};

    public String[] getIgnoreClientCode()
    {
        return ignoreClientCode;
    }

    public void setIgnoreClientCode(String[] ignoreClientCode)
    {
        this.ignoreClientCode = ignoreClientCode;
    }
}
