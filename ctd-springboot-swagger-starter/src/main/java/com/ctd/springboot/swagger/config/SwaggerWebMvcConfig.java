package com.ctd.springboot.swagger.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * SwaggerWebMvcConfig
 *
 * @author chentudong
 * @date 2020/3/28 13:04
 * @since 1.0
 */
public class SwaggerWebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置资源文件目录
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }
}
