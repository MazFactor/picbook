/**
 * Execute.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lyl.common.jiangda.service;

public class Execute  implements java.io.Serializable {
    private InputParameter requestContent;

    public Execute() {
    }

    public Execute(
           InputParameter requestContent) {
           this.requestContent = requestContent;
    }


    /**
     * Gets the requestContent value for this Execute.
     * 
     * @return requestContent
     */
    public InputParameter getRequestContent() {
        return requestContent;
    }


    /**
     * Sets the requestContent value for this Execute.
     * 
     * @param requestContent
     */
    public void setRequestContent(InputParameter requestContent) {
        this.requestContent = requestContent;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Execute)) return false;
        Execute other = (Execute) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestContent==null && other.getRequestContent()==null) || 
             (this.requestContent!=null &&
              this.requestContent.equals(other.getRequestContent())));
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
        if (getRequestContent() != null) {
            _hashCode += getRequestContent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Execute.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.jiangda.com", "execute"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestContent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.jiangda.com", "inputParameter"));
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
