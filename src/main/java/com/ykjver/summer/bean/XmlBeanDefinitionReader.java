package com.ykjver.summer.bean;

import com.ykjver.summer.core.ReflectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            Node node = beanElList.item(i);
            if (node instanceof Element) {
                Element item = (Element)node;
                String clazzName = item.getAttribute("class");
                String name = item.getAttribute("name");
                BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(name);
                ArrayList<String> dependOnList = new ArrayList<>();
                if (beanDefinition == null) {
                    beanDefinition = new BeanDefinition();
                    NodeList propertyList = item.getElementsByTagName("property");
                    if (propertyList != null && propertyList.getLength() > 0) {
                        for (int j = 0; j < propertyList.getLength(); j++) {
                            Element propertyEl = (Element)propertyList.item(j);
                            String propertyName = propertyEl.getAttribute("name");
                            String propertyValue = propertyEl.getAttribute("value");
                            String propertyRef = propertyEl.getAttribute("ref");
                            if(propertyRef != null && !propertyRef.isEmpty()) {
                                dependOnList.add(propertyRef);
                            }
                            BeanDefProperty beanDefProperty = new BeanDefProperty();
                            beanDefProperty.setName(propertyName);
                            beanDefProperty.setValue(propertyValue);
                            beanDefProperty.setRef(propertyRef);
                            beanDefinition.getPropertiesMap().put(propertyName, beanDefProperty);
                        }
                    }
                    beanDefinition.setName(name);
                    beanDefinition.setReferenceName(clazzName);
                    beanDefinition.setClassName(clazzName);
                    if (!dependOnList.isEmpty()) {
                        beanDefinition.setDependsOn(dependOnList.toArray(new String[dependOnList.size()]));
                    }
                    beanDefinitionRegistry.registryBeanDefinition(name, beanDefinition);
                }
            }
        }
    }
}
