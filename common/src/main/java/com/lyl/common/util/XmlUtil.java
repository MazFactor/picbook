package com.lyl.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @description: XML工具类
 * @date 2019/4/23 10:51
 */
public class XmlUtil {

    /**
    　* @功能描述: 根据xml字符串获得Document对象
    　* @param [xmlText]
    　* @return org.dom4j.Document
    　* @author lz
    　* @date 2019/4/23 11:45
    　*/
    public static Document getDocumentByXmlTest(String xmlText){
        if(xmlText==null || xmlText.equals("")){
            return null;
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlText);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
    　* @功能描述: 获得根据节点信息
    　* @param [xml] xml字符串
    　* @return org.dom4j.Element
    　* @author lz
    　* @date 2019/4/23 10:57
    　*/
    public static Element getRootElement(String xml){
        Document doc = null;
        Element root = null;

        try {
            doc = DocumentHelper.parseText(xml);
            root = doc.getRootElement();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return root;
    }

    /**
    　* @功能描述: 获得指定元素下所有节点属性及值
    　* @param [element]
    　* @return java.util.Map
    　* @author lz
    　* @date 2019/4/23 10:58
    　*/
    public static Map getNodeValues(Element element){
        Map map = new HashMap();
        try {
            List list = element.elements();
            Element e = null;
            for (int i = 0; i < list.size(); i++) {
                e = (Element)list.get(i);
                map.put(e.getName(), e.getText());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    /**
    　* @功能描述: 获得指定节点指定属性值
    　* @param [element, attributeName] 元素名称 属性名称
    　* @return java.lang.String
    　* @author lz
    　* @date 2019/4/23 10:58
    　*/
    public static String getElementAttributeValue(Element element,String attributeName){
        String value = "";
        try {
            value = element.attributeValue("attributeName");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
}
