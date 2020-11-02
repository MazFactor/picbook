package com.lyl.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
//    @Value("${spring.mvc.static-path-pattern}")
//    private String static_path_pattern;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/pic/**").addResourceLocations("file:D:/images/blog/");
//        registry.addResourceHandler(static_path_pattern).addResourceLocations("classpath:/pic/**");
        //        默认访问static文件
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
