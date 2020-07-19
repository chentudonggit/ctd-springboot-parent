package com.ctd.springboot.auth.store.jwt;

import com.ctd.springboot.auth.converter.authentication.CustomUserAuthenticationConverter;
import com.ctd.springboot.common.core.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JWT RSA 非对称加密令牌
 *
 * @author chentudong
 * @date 2020/3/7 16:15
 * @since 1.0
 */
@Component
public class AuthJwtTokenStore {
    @Bean("keyProp")
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyProperties.KeyStore keyStore = keyProperties.getKeyStore();
        org.springframework.core.io.Resource resource = null;
        String alias = null;
        char[] chars = new char[0];
        if (Objects.nonNull(keyStore)) {
            resource = keyStore.getLocation();
            alias = keyStore.getAlias();
            String secret = keyStore.getSecret();
            if (StringUtils.isNoneBlank(secret)) {
                chars = secret.toCharArray();
            }
        }
        KeyPair keyPair = new KeyStoreKeyFactory(resource, chars).getKeyPair(alias);
        converter.setKeyPair(keyPair);
        DefaultAccessTokenConverter tokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return converter;
    }

    /**
     * jwt 生成token 定制化处理
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(1);
            Object principal = authentication.getPrincipal();
            //增加id参数
            if (principal instanceof UserVO) {
                UserVO user = (UserVO) principal;
                additionalInfo.put("id", user.getId());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
