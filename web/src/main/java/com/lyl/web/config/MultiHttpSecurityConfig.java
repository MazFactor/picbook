package com.lyl.web.config;

import com.lyl.web.base.Entity.WebSecurityProperties;
import com.lyl.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Autowired
    private static WebSecurityProperties webSecurityProperties;
    @Autowired
    private static AccountService accountService;

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        // ensure the passwords are encoded properly
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
        return manager;
    }

    //
//    @Configuration
//    @Order(1)
//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    //允许所有用户访问"/","/jquery/**","/semantic/**","/css/**","/js/**","/images/**"
//                    .antMatchers("/",
//                            "/stylesheets/**",
//                            "/scripts/**",
//                            "/images/**",
//                            "/prism/**",
//                            "/modules/**",
//                            "/**/favicon.ico").permitAll()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/css/**").permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage(webSecurityProperties.getLoginPage())
//                    .defaultSuccessUrl("/secondary")
//                    .and()
//                    .httpBasic()
//            ;
//        }
//    }

    //*****************************表单认证*******************************//
//    @Configuration
//    @Order(2)
//    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin();
//        }
//    }
    //*****************************数据库账户认证*******************************//
//    @Configuration
//    @Order(3)
//    public static class CustomLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(accountService);
//        }
//    }
}
