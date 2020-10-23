package com.ctd.springboot.data.mongodb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * MongodbAutoConfiguration
 *
 * @author chentudong
 * @date 2020/10/23 21:52
 * @since 1.0
 */
@Order(-1)
@Configuration
@ComponentScan
@PropertySource(value = {"classpath:/config/application.yml"}, ignoreResourceNotFound = true)
public class MongodbAutoConfiguration {
}
