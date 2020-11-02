/**
 * ExecuteResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lyl.common.jiangda.service;

public class ExecuteResponse  implements java.io.Serializable {
    private OutputParameter responseContent;

    public ExecuteResponse() {
    }

    public ExecuteResponse(
           OutputParameter responseContent) {
           this.responseContent = responseContent;
    }


    /**
     * Gets the responseContent value for this ExecuteResponse.
     * 
     * @return responseContent
     */
    public OutputParameter getResponseContent() {
        return responseContent;
    }


    /**
     * Sets the responseContent value for this ExecuteResponse.
     * 
     * @param responseContent
     */
    public void setResponseContent(OutputParameter responseContent) {
        this.responseContent = responseContent;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ExecuteResponse)) return false;
        ExecuteResponse other = (ExecuteResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseContent==null && other.getResponseContent()==null) || 
             (this.responseContent!=null &&
              this.responseContent.equals(other.getResponseContent())));
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
        if (getResponseContent() != null) {
            _hashCode += getResponseContent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExecuteResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.jiangda.com", "executeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseContent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.jiangda.com", "outputParameter"));
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
