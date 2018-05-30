package com.ykjver.summer.bean;

import java.util.List;

/**
 * @author yk
 * @date 2018/5/28
 */
public class BeanDefinition {
    private String name;
    private String ReferenceName;
    private String className;
    private String[] dependsOn;
    private String scope;
    List<String> properties;

    public boolean isSingleton() {
        return BeanFactory.SCOPE_SINGLETON.equals(scope) || BeanFactory.SCOPE_DEFAULT.equals(scope);
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
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
