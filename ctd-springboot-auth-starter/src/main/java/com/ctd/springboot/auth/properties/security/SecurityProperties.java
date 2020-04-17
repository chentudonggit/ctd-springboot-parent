package com.ctd.springboot.auth.properties.security;

import com.ctd.springboot.auth.properties.AuthProperties;
import com.ctd.springboot.auth.properties.PermitProperties;
import com.ctd.springboot.auth.properties.validate.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * SecurityProperties
 *
 * @author chentudong
 * @date 2020/3/7 15:56
 * @since 1.0
 */
@ConfigurationProperties(prefix = "ctd.security")
@RefreshScope
public class SecurityProperties
{
    /**
     * 认证
     */
    private AuthProperties auth = new AuthProperties();

    /**
     * 忽略
     */
    private PermitProperties ignore = new PermitProperties();

    /**
     * 验证码
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public AuthProperties getAuth()
    {
        return auth;
    }

    public void setAuth(AuthProperties auth)
    {
        this.auth = auth;
    }

    public PermitProperties getIgnore()
    {
        return ignore;
    }

    public void setIgnore(PermitProperties ignore)
    {
        this.ignore = ignore;
    }

    public ValidateCodeProperties getCode()
    {
        return code;
    }

    public void setCode(ValidateCodeProperties code)
    {
        this.code = code;
    }
}
