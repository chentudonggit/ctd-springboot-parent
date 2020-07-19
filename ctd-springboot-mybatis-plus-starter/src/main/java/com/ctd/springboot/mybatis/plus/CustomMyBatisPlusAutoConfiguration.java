package com.ctd.springboot.mybatis.plus;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * MyBatisPlusAutoConfiguration
 *
 * @author chentudong
 * @date 2020/3/8 9:08
 * @since 1.0
 */
@Order(-1)
@Configuration
@ComponentScan
public class CustomMyBatisPlusAutoConfiguration {
}
