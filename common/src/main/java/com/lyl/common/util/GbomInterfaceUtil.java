package com.jinghuan.common.util;

import com.jinghuan.common.jiangda.service.*;
import com.jinghuan.common.jiangda.entity.GbomInterfaceEntity;
//import com.gw.mdm.common.jiangda.service.*;
import com.jinghuan.common.jiangda.service.*;

import java.net.URL;


/**
 * gbom接口调用通用类
 * @Author: chenlg
 * @Date: 2019/7/10 11:03
 * @Version 1.0
 */
public class GbomInterfaceUtil {


    /**
     * gbom接口调用通用方法
     * @param model gbom接口实体类
     * @return
     */
    public static OutputParameter interfaceInit(GbomInterfaceEntity model){
        OutputParameter outputParameter = null;
        try{
            URL url=new URL(model.getWsdlUrl());
            JDIntegrationEndpointServiceLocator locator=new JDIntegrationEndpointServiceLocator();
            AuthInfo authInfo= new AuthInfo();
            //方法名
            authInfo.setBusinessType(model.getBusinessType());
            authInfo.setCredential(model.getCredential());
            authInfo.setPrincipal(model.getPrincipal());
            authInfo.setSystemName(model.getSystemName());
            Execute ex=new Execute();
            InputParameter input=new InputParameter();
            //流水号
            input.setBizSerialNum(model.getBizSerialNum());
            input.setContent(model.getContent());
            ex.setRequestContent(input);
            JDIntegrationEndpoint endPoint=locator.getJDIntegrationEndpointPort(url);
            ExecuteResponse response = endPoint.execute(ex, authInfo);
            outputParameter = response.getResponseContent();
            outputParameter.getBizSerialNum(); // 流水号
            outputParameter.getContent(); // 内容 xml字符串
            outputParameter.getErrorCode(); // 错误代码
            outputParameter.getErrorMessage(); // 错误信息
            outputParameter.getStatus(); // 状态:success  failed
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputParameter;
    }
}
