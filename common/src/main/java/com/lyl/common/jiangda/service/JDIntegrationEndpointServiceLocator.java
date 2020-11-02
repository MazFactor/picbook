/**
 * JDIntegrationEndpointServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lyl.common.jiangda.service;

public class JDIntegrationEndpointServiceLocator extends org.apache.axis.client.Service implements JDIntegrationEndpointService {



    public JDIntegrationEndpointServiceLocator() {
    }


    public JDIntegrationEndpointServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JDIntegrationEndpointServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for JDIntegrationEndpointPort http://gwbom.corp.com/services/JDIntegration
    private String JDIntegrationEndpointPort_address ="http://10.255.19.36/services/JDIntegration";

    public String getJDIntegrationEndpointPortAddress() {
        return JDIntegrationEndpointPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String JDIntegrationEndpointPortWSDDServiceName = "JDIntegrationEndpointPort";

    public String getJDIntegrationEndpointPortWSDDServiceName() {
        return JDIntegrationEndpointPortWSDDServiceName;
    }

    public void setJDIntegrationEndpointPortWSDDServiceName(String name) {
        JDIntegrationEndpointPortWSDDServiceName = name;
    }

    public JDIntegrationEndpoint getJDIntegrationEndpointPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JDIntegrationEndpointPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJDIntegrationEndpointPort(endpoint);
    }

    public JDIntegrationEndpoint getJDIntegrationEndpointPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            JDIntegrationEndpointServiceSoapBindingStub _stub = new JDIntegrationEndpointServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getJDIntegrationEndpointPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setJDIntegrationEndpointPortEndpointAddress(String address) {
        JDIntegrationEndpointPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (JDIntegrationEndpoint.class.isAssignableFrom(serviceEndpointInterface)) {
                JDIntegrationEndpointServiceSoapBindingStub _stub = new JDIntegrationEndpointServiceSoapBindingStub(new java.net.URL(JDIntegrationEndpointPort_address), this);
                _stub.setPortName(getJDIntegrationEndpointPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("JDIntegrationEndpointPort".equals(inputPortName)) {
            return getJDIntegrationEndpointPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.jiangda.com", "JDIntegrationEndpointService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.jiangda.com", "JDIntegrationEndpointPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("JDIntegrationEndpointPort".equals(portName)) {
            setJDIntegrationEndpointPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
