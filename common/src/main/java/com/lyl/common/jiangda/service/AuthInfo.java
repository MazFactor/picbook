/**
 * AuthInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jinghuan.common.jiangda.service;

public class AuthInfo  implements java.io.Serializable {
    private String businessType;

    private String credential;

    private String principal;

    private String systemName;

    public AuthInfo() {
    }

    public AuthInfo(
           String businessType,
           String credential,
           String principal,
           String systemName) {
           this.businessType = businessType;
           this.credential = credential;
           this.principal = principal;
           this.systemName = systemName;
    }


    /**
     * Gets the businessType value for this AuthInfo.
     * 
     * @return businessType
     */
    public String getBusinessType() {
        return businessType;
    }


    /**
     * Sets the businessType value for this AuthInfo.
     * 
     * @param businessType
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }


    /**
     * Gets the credential value for this AuthInfo.
     * 
     * @return credential
     */
    public String getCredential() {
        return credential;
    }


    /**
     * Sets the credential value for this AuthInfo.
     * 
     * @param credential
     */
    public void setCredential(String credential) {
        this.credential = credential;
    }


    /**
     * Gets the principal value for this AuthInfo.
     * 
     * @return principal
     */
    public String getPrincipal() {
        return principal;
    }


    /**
     * Sets the principal value for this AuthInfo.
     * 
     * @param principal
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }


    /**
     * Gets the systemName value for this AuthInfo.
     * 
     * @return systemName
     */
    public String getSystemName() {
        return systemName;
    }


    /**
     * Sets the systemName value for this AuthInfo.
     * 
     * @param systemName
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AuthInfo)) return false;
        AuthInfo other = (AuthInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.businessType==null && other.getBusinessType()==null) || 
             (this.businessType!=null &&
              this.businessType.equals(other.getBusinessType()))) &&
            ((this.credential==null && other.getCredential()==null) || 
             (this.credential!=null &&
              this.credential.equals(other.getCredential()))) &&
            ((this.principal==null && other.getPrincipal()==null) || 
             (this.principal!=null &&
              this.principal.equals(other.getPrincipal()))) &&
            ((this.systemName==null && other.getSystemName()==null) || 
             (this.systemName!=null &&
              this.systemName.equals(other.getSystemName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getBusinessType() != null) {
            _hashCode += getBusinessType().hashCode();
        }
        if (getCredential() != null) {
            _hashCode += getCredential().hashCode();
        }
        if (getPrincipal() != null) {
            _hashCode += getPrincipal().hashCode();
        }
        if (getSystemName() != null) {
            _hashCode += getSystemName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.jiangda.com", "authInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "businessType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credential");
        elemField.setXmlName(new javax.xml.namespace.QName("", "credential"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("principal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "principal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
