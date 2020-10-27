package com.lyl.web.config;

//import com.jinghuan.zlog.IndexPattern;
//import com.jinghuan.zlog.ZLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LogConfig {
    @Value("${zlog.companyname}")
    private String companyName;
    @Value("${zlog.platformname}")
    private String platformName;
    @Value("${zlog.projectname}")
    private String projectName;
    @Value("${spring.application.name}")
    private String servicetName;
    @Value("${zlog.default.indexname}")
    private String defaultIndexName;
    @Value("${zlog.default.logtype}")
    private String defaultLogType;

    public LogConfig() {
    }

    @PostConstruct
    public void init() {
//        ZLoggerFactory.setSimpleBaseParams(this.getCompanyName(),this.getPlatformName().toLowerCase(), this.getProjectName().toLowerCase(), this.servicetName);
//        ZLoggerFactory.setDefaultIndexName(this.defaultIndexName);
//        ZLoggerFactory.setDefaultIndexPattern(IndexPattern.MONTH);
//        ZLoggerFactory.setDefaultLogType(this.defaultLogType);
//        ZLoggerFactory.setDefaultOperationType("create");
    }

    public String getCompanyName() {return companyName;}

    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getPlatformName() {
        return this.platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getServicetName() {
        return this.servicetName;
    }

    public void setServicetName(String servicetName) {
        this.servicetName = servicetName;
    }

    public String getDefaultIndexName() {
        return this.defaultIndexName;
    }

    public void setDefaultIndexName(String defaultIndexName) {
        this.defaultIndexName = defaultIndexName;
    }

    public String getDefaultLogType() {
        return this.defaultLogType;
    }

    public void setDefaultLogType(String defaultLogType) {
        this.defaultLogType = defaultLogType;
    }
}
