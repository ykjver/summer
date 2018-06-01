package com.ykjver.summer.bean;

/**
 * @author ykjver@gmail.com
 * @date 2018/6/1
 */
public interface BeanDefinitionRegistry {
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition);
    BeanDefinition getBeanDefinition(String beanName);
}
