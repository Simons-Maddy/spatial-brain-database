package com.spatial.spatialbrain.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class UpLoadConfig {
    @Bean
    public MultipartConfigElement getMultipartConfig() {
        MultipartConfigFactory config = new MultipartConfigFactory();
        // 设置上传文件的单个大小限制
        config.setMaxRequestSize(DataSize.parse("200MB"));
        // 设置总的上传的大小限制
        config.setMaxRequestSize(DataSize.parse("500MB"));
        // 设置临时保存目录
        /*config.setLocation("/Users/chunfu/Desktop");*/
        // 创建一个上传配置并返回
        return config.createMultipartConfig();
    }
}
