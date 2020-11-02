package com.lyl.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring上下文工具类
 *
 * @author WRQ
 * @date 2019/6/26
 * @since 1.0.0
 */
public class SpringContextUtil implements ApplicationContextAware {
  
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }  

    public static ApplicationContext getApplicationContext() {
        return applicationContext;  
    }  

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);  
    }

    public static <T> T getBean(Class<T> var1) throws BeansException {
        return applicationContext.getBean(var1);
    }
}  