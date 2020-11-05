package com.lyl.web.config;

import com.lyl.web.controller.DispatchController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * 配置文件
     */
    private static final Properties PROPERTIES = new Properties();
    /**
     * 加载配置信息
     */
    static {
        try {
            PROPERTIES.load(DispatchController.class.getClassLoader().getResourceAsStream("image-upload.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler是指你想在url请求的路径，addResourceLocations是图片存放的真实路
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        //windows
        registry.addResourceHandler("/pic/**").addResourceLocations("file:D:/images/blog/");
        //linux
//        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + PROPERTIES.getProperty("web.image.uploadPath"));
//        registry.addResourceHandler(static_path_pattern).addResourceLocations("classpath:/pic/**");
        //        默认访问static文件
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
