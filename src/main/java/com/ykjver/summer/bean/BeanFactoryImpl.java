package com.ykjver.summer.bean;

import com.ykjver.summer.Main;
import com.ykjver.summer.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ykjver@gmail.com
 * @date 2018/5/28
 */
public class BeanFactoryImpl implements BeanFactory{

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private Map<String, WrapBean> beanMap = new HashMap<>();

    public Object getBean(String name) {
        WrapBean wrapBean = beanMap.get(name);
        if (wrapBean != null) {
            return wrapBean.getBean();
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            return null;
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(beanDefinition.getReferenceName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        wrapBean = new WrapBean();
        wrapBean.setBean(obj);
        beanMap.put(name, wrapBean);
        return obj;
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(resource.load());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList childNodes = document.getChildNodes();
        Node beansNode = childNodes.item(0);
        Element beansElement = (Element) beansNode;
        NodeList beanElList = beansElement.getElementsByTagName("bean");
        for (int i = 0; i < beanElList.getLength(); i++) {
            Element item = (Element)beanElList.item(i);
            String clazzName = item.getAttribute("class");
            String name = item.getAttribute("name");
            BeanDefinition beanDefinition = beanDefinitionMap.get(name);
            if (beanDefinition == null) {
                beanDefinition = new BeanDefinition();
                beanDefinition.setName(name);
                beanDefinition.setReferenceName(clazzName);
                beanDefinitionMap.put(name, beanDefinition);
            }
        }
    }
}
