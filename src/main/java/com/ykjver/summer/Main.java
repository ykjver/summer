package com.ykjver.summer;

import com.ykjver.summer.bean.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author ykjver@gmail.com
 * @date 2018/5/28
 */
public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassPathResource classPathResource = new ClassPathResource("bean.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(classPathResource);
        Student student = (Student)beanFactory.getBean("student");
        student.say();
    }
}
