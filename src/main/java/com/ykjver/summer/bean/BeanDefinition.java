package com.ykjver.summer.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @date 2018/5/28
 */
public class BeanDefinition {
    private String name;
    private String ReferenceName;
    private String className;
    private String[] dependsOn = new String[0];
    private String scope = "";
    Map<String, BeanDefProperty> propertiesMap = new HashMap<>();

    public boolean isSingleton() {
        return BeanFactory.SCOPE_SINGLETON.equals(scope) || BeanFactory.SCOPE_DEFAULT.equals(scope);
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Map<String, BeanDefProperty> getPropertiesMap() {
        return propertiesMap;
    }

    public void setPropertiesMap(Map<String, BeanDefProperty> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferenceName() {
        return ReferenceName;
    }

    public void setReferenceName(String referenceName) {
        ReferenceName = referenceName;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
