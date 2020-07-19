package com.ctd.springboot.auth.enums.token;

/**
 * TokenStoreType
 *
 * @author chentudong
 * @date 2020/3/7 21:03
 * @since 1.0
 */
public enum TokenStoreType {
    /**
     * db
     */
    db,
    /**
     * jwt
     */
    jwt,

    /**
     * jwtRsa
     */
    jwtRsa,

    /**
     * redis
     */
    redis;
}
