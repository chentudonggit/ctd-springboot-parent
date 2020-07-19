package com.ctd.springboot.auth.token.openid;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * OpenIdAuthenticationToken
 *
 * @author chentudong
 * @date 2020/3/7 20:17
 * @since 1.0
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;

    /**
     * {@link #isAuthenticated()}
     *
     * @param openId openId
     */
    public OpenIdAuthenticationToken(String openId) {
        super(null);
        this.principal = openId;
        setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            AssertUtils.msgDevelopment(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
