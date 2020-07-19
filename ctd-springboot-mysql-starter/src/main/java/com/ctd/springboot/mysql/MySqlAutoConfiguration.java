package com.ctd.springboot.mysql;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * MySqlAutoConfiguration
 *
 * @author chentudong
 * @date 2020/4/16 4:17 下午
 * @since 1.0
 */
@Order(-1)
@Configuration
@ComponentScan
@PropertySource(value = {"classpath:/config/application.yml"}, ignoreResourceNotFound = true)
public class MySqlAutoConfiguration {
}
