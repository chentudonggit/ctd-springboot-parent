package com.ctd.springboot.mybatis.plus.processor;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * CustomEnvironmentPostProcessor
 *
 * @author chentudong
 * @date 2020/5/10 21:11
 * @since 1.0
 */
@Component
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor
{
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application)
    {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource("/mybatis/application.yml");
        yaml.setResources(classPathResource);
        Properties properties = yaml.getObject();
        assert properties != null;
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource("custom", properties);
        environment.getPropertySources().addFirst(propertiesPropertySource);
    }
}
