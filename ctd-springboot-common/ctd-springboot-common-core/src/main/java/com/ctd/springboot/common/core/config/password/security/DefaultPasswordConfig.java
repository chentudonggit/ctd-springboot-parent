package com.ctd.springboot.common.core.config.password.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DefaultPasswordConfig
 *
 * @author chentudong
 * @date 2020/3/8 17:08
 * @since 1.0
 */
public class DefaultPasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
