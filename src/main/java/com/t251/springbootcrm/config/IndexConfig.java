package com.t251.springbootcrm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author world
 * 设置首页
 */
@Configuration
public class IndexConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
       registry.addViewController("/").setViewName("forward:/login");
    }
}
