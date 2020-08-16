package com.ctd.springboot.cloud;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * CloudEnableAutoConfiguration
 *
 * @author LYJ
 * @date 2020/8/13 20:39ErrorProperties
 * @since 1.0
 */
@Configuration
@Import(ErrorProperties.class)
public class CloudEnableAutoConfiguration {
    private final ErrorProperties errorProperties;
    public CloudEnableAutoConfiguration(ErrorProperties errorProperties) {
        this.errorProperties = errorProperties;
    }
}
