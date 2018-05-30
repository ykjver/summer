package com.ykjver.summer.bean;

/**
 * @author yangke
 * @date 2018/5/28
 */
public interface BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    String SCOPE_DEFAULT = "";

    Object getBean(String name) throws RuntimeException;
}
