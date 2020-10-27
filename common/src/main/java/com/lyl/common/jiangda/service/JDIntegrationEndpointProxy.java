package com.jinghuan.common.jiangda.service;

public class JDIntegrationEndpointProxy implements JDIntegrationEndpoint {
  private String _endpoint = null;
  private JDIntegrationEndpoint jDIntegrationEndpoint = null;
  
  public JDIntegrationEndpointProxy() {
    _initJDIntegrationEndpointProxy();
  }
  
  public JDIntegrationEndpointProxy(String endpoint) {
    _endpoint = endpoint;
    _initJDIntegrationEndpointProxy();
  }
  
  private void _initJDIntegrationEndpointProxy() {
    try {
      jDIntegrationEndpoint = (new JDIntegrationEndpointServiceLocator()).getJDIntegrationEndpointPort();
      if (jDIntegrationEndpoint != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)jDIntegrationEndpoint)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)jDIntegrationEndpoint)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (jDIntegrationEndpoint != null)
      ((javax.xml.rpc.Stub)jDIntegrationEndpoint)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public JDIntegrationEndpoint getJDIntegrationEndpoint() {
    if (jDIntegrationEndpoint == null)
      _initJDIntegrationEndpointProxy();
    return jDIntegrationEndpoint;
  }
  
  public ExecuteResponse execute(Execute parameters, AuthInfo authInfo) throws java.rmi.RemoteException{
    if (jDIntegrationEndpoint == null)
      _initJDIntegrationEndpointProxy();
    return jDIntegrationEndpoint.execute(parameters, authInfo);
  }
  
  
}