package com.ctd.springboot.auth.token;

import com.ctd.springboot.auth.vo.user.login.LoginUserVO;
import com.ctd.springboot.common.core.bean.BeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * CustomDefaultAuthenticationKeyGenerator
 *
 * @author chentudong
 * @date 2020/7/19 15:34
 * @since 1.0
 */
public class CustomDefaultAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {
    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            values.put("username", authentication.getName());
        }

        values.put("client_id", authorizationRequest.getClientId());
        if (Objects.nonNull(authorizationRequest.getScope())) {
            values.put("scope", OAuth2Utils.formatParameterList(new TreeSet(authorizationRequest.getScope())));
        }
        Authentication userAuthentication = authentication.getUserAuthentication();
        if (Objects.nonNull(userAuthentication)) {
            Object principal = userAuthentication.getPrincipal();
            String authenticationName = userAuthentication.getName();
            LoginUserVO loginUser = BeanHelper.convert(principal, LoginUserVO.class);
            if (principal instanceof LoginUserVO && Objects.nonNull(loginUser)) {
                values.put("mobile", loginUser.getMobile());
            } else if (StringUtils.isNotBlank(authenticationName)) {
                values.put("authenticationName", authenticationName);
            }
        }
        return this.generateKey(values);
    }
}
