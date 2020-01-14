package com.yz.common.core.utils;

import org.apache.http.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by yangzhao on 17/8/8.
 */
public class XMLUtil {
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromXML(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            return parseElements(elements);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromXML(InputStream is) {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(is);
            Element rootElement = document.getRootElement();
            return parseElements(rootElement.elements());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> parseElements(List<Element> elements) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Element element : elements) {
            String key = element.getName();
            String value = element.getText();
            map.put(key, value);
        }
        return map;
    }
    /**
     * 请求参数转换xml
     * @param params
     * @return
     */
    public static String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String toXml(Map<String,String> map){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        Set keySet = map.keySet();
        Iterator<String> iterator=keySet.iterator();
        while(iterator.hasNext()){
            String key=iterator.next();
            String value=map.get(key);
            sb.append("<" +key+ ">");
            sb.append(value);
            sb.append("</" +key + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * xml格式化
     * @param inputXML
     * @return
     * @throws Exception
     */
    public static String formatXML(String inputXML) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(inputXML));
        String requestXML = null;
        XMLWriter writer = null;
        if (document != null) {
            try {
                StringWriter stringWriter = new StringWriter();
                OutputFormat format = new OutputFormat(" ", true);
                writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                requestXML = stringWriter.getBuffer().toString();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return requestXML;
    }
}
