package com.ctd.springboot.auth.config.mvc;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * DefaultWebMvcConfig
 *
 * @author chentudong
 * @date 2020/3/8 8:58
 * @since 1.0
 */
public class DefaultWebMvcConfig extends WebMvcConfigurationSupport
{
    /**
     * 设置资源文件目录
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }
}
