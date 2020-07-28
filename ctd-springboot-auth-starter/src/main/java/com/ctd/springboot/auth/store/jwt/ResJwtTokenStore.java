package com.ctd.springboot.auth.store.jwt;

import com.ctd.springboot.auth.converter.authentication.CustomUserAuthenticationConverter;
import com.ctd.springboot.common.core.constants.security.SecurityConstants;
import com.ctd.springboot.common.core.utils.param.ParamUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资源服务器 TokenStore
 * JWT RSA 非对称加密
 *
 * @author chentudong
 * @date 2020/3/7 20:09
 * @since 1.0
 */
@Component
public class ResJwtTokenStore {
    @Autowired
    private ResourceServerProperties resource;

    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        DefaultAccessTokenConverter tokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return converter;
    }

    /**
     * 获取非对称加密公钥 Key
     *
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource res = new ClassPathResource(SecurityConstants.RSA_PUBLIC_KEY);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return getKeyFromAuthorizationServer();
        }
    }

    /**
     * 通过访问授权服务器获取非对称加密公钥 Key
     *
     * @return 公钥 Key
     */
    private String getKeyFromAuthorizationServer() {
        if (StringUtils.isNotBlank(this.resource.getJwt().getKeyUri())) {
            final HttpHeaders headers = new HttpHeaders();
            final String username = this.resource.getClientId();
            final String password = this.resource.getClientSecret();
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                final byte[] token = Base64.getEncoder().encode((username + ":" + password).getBytes());
                headers.add("Authorization", "Basic " + new String(token));
            }
            final HttpEntity<Void> request = new HttpEntity<>(headers);
            final String url = this.resource.getJwt().getKeyUri();
            Map body = new RestTemplate().exchange(url, HttpMethod.GET, request, Map.class).getBody();
            return ParamUtils.getParam(body, "value");
        }
        return null;
    }
}
