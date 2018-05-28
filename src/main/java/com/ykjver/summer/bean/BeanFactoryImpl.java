package com.ykjver.summer.bean;

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
        return null;
    }
}
