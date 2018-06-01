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

/**
 * @author ykjver@gmail.com
 * @date 2018/5/30
 */
public class XmlBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinitions(Resource resource) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(resource.getInputStream());
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
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(name);
            if (beanDefinition == null) {
                beanDefinition = new BeanDefinition();
                beanDefinition.setName(name);
                beanDefinition.setReferenceName(clazzName);
                beanDefinition.setClassName(clazzName);
                beanDefinitionRegistry.registryBeanDefinition(name, beanDefinition);
            }
        }
    }
}
