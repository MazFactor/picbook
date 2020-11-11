package com.lyl.web.base.Entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Security 配置
 *
 * @author bojiangzhou 2018/03/28
 */
@Component
//@ConfigurationProperties(prefix = WebSecurityProperties.PREFIX)
public class WebSecurityProperties {

//    public static final String PREFIX = "lyl.security";

    /**
     * 登录页面
     */
    private String loginPage = "/login";


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

}
