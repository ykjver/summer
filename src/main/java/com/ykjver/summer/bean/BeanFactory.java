package com.ykjver.summer.bean;

/**
 * @author yangke
 * @date 2018/5/28
 */
public interface BeanFactory {
    public Object getBean(String name);
    public void loadBeanDefinition(Resource resource);
}
