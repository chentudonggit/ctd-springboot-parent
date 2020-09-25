package com.ctd.springboot.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * OracleAutoConfiguration
 *
 * @author chentudong
 * @date 2020/9/25 10:32
 * @since 1.0
 */
@Order(-1)
@Configuration
@ComponentScan
@PropertySource(value = {"classpath:/config/application.yml"}, ignoreResourceNotFound = true)
public class OracleAutoConfiguration {
}
