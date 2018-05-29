package com.ykjver.summer;

import com.ykjver.summer.bean.BeanFactory;
import com.ykjver.summer.bean.BeanFactoryImpl;
import com.ykjver.summer.bean.FileResource;
import com.ykjver.summer.bean.Resource;
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
        BeanFactory beanFactory = new BeanFactoryImpl();
        Resource resource = new FileResource();
        beanFactory.loadBeanDefinition(resource);
        Student student = (Student)beanFactory.getBean("student");
        student.say();
    }
}
