package com.jinghuan.common.jiangda.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * G-BOM接口通用-实体类
 * @说明：读取yml配置文件自动封装成实体类
 * @Author: chenlg
 * @Date: 2019/7/10 10:30
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "gbom")
public class GbomInterfaceEntity {

    /*
     * wsdl地址
     */
    private String wsdlUrl;

    /**
     * 凭证
     */
    private String credential;

    /**
     * 调用人
     */
    private String principal;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 调用的接口
     */
    private String businessType;

    /**
     * 流水号
     */
    private String bizSerialNum;

    /**
     * request请求参数
     */
    private String content;

    /**
     * G-BOM生成接口文件中
     * JDIntegrationEndpointServiceLocator中的地址
     */
    private String address;



    public String getWsdlUrl() {
        return wsdlUrl;
    }

    public void setWsdlUrl(String wsdlUrl) {
        this.wsdlUrl = wsdlUrl;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBizSerialNum() {
        return bizSerialNum;
    }

    public void setBizSerialNum(String bizSerialNum) {
        this.bizSerialNum = bizSerialNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
