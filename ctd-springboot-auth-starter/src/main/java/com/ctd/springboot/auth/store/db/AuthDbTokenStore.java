package com.ctd.springboot.auth.store.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 数据库存取令牌
 *
 * @author chentudong
 * @date 2020/3/7 16:08
 * @since 1.0
 */
public class AuthDbTokenStore
{
    @Autowired(required = false)
    private DataSource dataSource;

    public TokenStore tokenStore()
    {
        return new JdbcTokenStore(dataSource);
    }
}
