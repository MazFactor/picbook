/**
 * JDIntegrationEndpointService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lyl.common.jiangda.service;

public interface JDIntegrationEndpointService extends javax.xml.rpc.Service {
    String getJDIntegrationEndpointPortAddress();

    JDIntegrationEndpoint getJDIntegrationEndpointPort() throws javax.xml.rpc.ServiceException;

    JDIntegrationEndpoint getJDIntegrationEndpointPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
