package com.ykjver.summer.bean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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

    public void loadBeanDefinition(Resource resource) {

    }
}