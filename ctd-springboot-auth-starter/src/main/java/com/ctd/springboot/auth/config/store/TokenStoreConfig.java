package com.ctd.springboot.auth.config.store;

import com.ctd.springboot.auth.enums.token.TokenStoreType;
import com.ctd.springboot.auth.properties.TokenStoreProperties;
import com.ctd.springboot.auth.store.db.AuthDbTokenStore;
import com.ctd.springboot.auth.store.jwt.AuthJwtTokenStore;
import com.ctd.springboot.auth.store.jwt.ResJwtTokenStore;
import com.ctd.springboot.auth.store.redis.AuthRedisTokenStore;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * TokenStoreConfig
 *
 * @author chentudong
 * @date 2020/3/7 20:52
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(TokenStoreProperties.class)
public class TokenStoreConfig {
    @Autowired(required = false)
    private AuthDbTokenStore authDbTokenStore;
    private final TokenStoreProperties tokenStoreProperties;
    private final AuthRedisTokenStore authRedisTokenStore;
    private final AuthJwtTokenStore authJwtTokenStore;
    private final ResJwtTokenStore resJwtTokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    public TokenStoreConfig(TokenStoreProperties tokenStoreProperties,
                            AuthRedisTokenStore authRedisTokenStore,
                            AuthJwtTokenStore authJwtTokenStore,
                            ResJwtTokenStore resJwtTokenStore,
                            JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.tokenStoreProperties = tokenStoreProperties;
        this.authRedisTokenStore = authRedisTokenStore;
        this.authJwtTokenStore = authJwtTokenStore;
        this.resJwtTokenStore = resJwtTokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Primary
    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    public TokenStore tokenStore() {
        TokenStoreType type = tokenStoreProperties.getType();
        if (TokenStoreType.jwtRsa.equals(type)) {
            return resJwtTokenStore.tokenStore(jwtAccessTokenConverter);
        } else if (TokenStoreType.db.equals(type)) {
            AssertUtils.isNull(authDbTokenStore, "token:store:type：db，请配置数据库。");
            authDbTokenStore.tokenStore();
        } else if (TokenStoreType.jwt.equals(type)) {
            return authJwtTokenStore.tokenStore(jwtAccessTokenConverter);
        }
        return authRedisTokenStore.tokenStore();
    }

    public TokenStore getTokenStore() {
        return tokenStore();
    }
}
