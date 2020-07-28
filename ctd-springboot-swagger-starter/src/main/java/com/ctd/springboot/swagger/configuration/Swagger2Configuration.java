package com.ctd.springboot.swagger.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * Swagger2Configuration
 *
 * @author chentudong
 * @date 2020/7/27 21:35
 * @since 1.0
 */
@ConditionalOnProperty(name = "ctd.swagger.enabled", matchIfMissing = true)
@Import({Swagger2DocumentationConfiguration.class})
public class Swagger2Configuration {
}
