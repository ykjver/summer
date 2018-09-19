package com.ykjver.summer.bean;

import com.ykjver.summer.bean.exception.CreateBeanException;
import com.ykjver.summer.core.ClassUtils;
import com.ykjver.summer.core.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author ykjver@gmail.com
 * @date 2018/5/29
 */
public class XmlBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beandefinitions = new HashMap<>(256);

    private final Map<String, Object> singletonObjects = new HashMap<>(256);

    private final Set<String> beansOnCreate = new HashSet<>(16);

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String name) throws RuntimeException {
        Object bean = null;
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            return sharedInstance;
        }
        final BeanDefinition bd = getBeanDefinition(name);
        if (bd == null) {
            throw new CreateBeanException("bean is not exist, beanName: " + name);
        }
        String[] dependsOn = bd.getDependsOn();
        if (dependsOn != null) {
            for (String dep : dependsOn) {
                if (beansOnCreate.contains(name)) {
                    throw new CreateBeanException("circle depend, bean name: "
                            + dep + ", depend: " + name);
                }
                beansOnCreate.add(name);
                getBean(dep);
            }
        }

        if(bd.isSingleton()) {
            bean = createBean(name, bd, null);
            populateBean(bean, bd);
        }
        singletonObjects.put(name, bean);
        beansOnCreate.remove(name);
        return bean;
    }

    private void populateBean(Object bean, BeanDefinition bd) {
        Map<String, BeanDefProperty> propertiesMap = bd.getPropertiesMap();
        if (propertiesMap != null && !propertiesMap.isEmpty()) {
            for (Map.Entry<String, BeanDefProperty> propertyEntry : propertiesMap.entrySet()) {
                String name = propertyEntry.getKey();
                BeanDefProperty property = propertyEntry.getValue();
                if (property.getRef() != null && !property.getRef().isEmpty()) {
                    ReflectUtils.setFieldRefValue(bean, property.getRef(), getBean(property.getRef()));
                } else {
                    ReflectUtils.setFiledValue(bean,
                            property.getName(),
                            property.getValue());
                }
            }
        }

        String[] dependsOn = bd.getDependsOn();
        for (String s : dependsOn) {
            ReflectUtils.setFieldRefValue(bean, s, getBean(s));
        }
    }

    private Object getSingleton(String beanName) {
        Objects.requireNonNull(beanName, "bean name can not be null");
        return singletonObjects.get(beanName);
    }

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        Objects.requireNonNull(beanName, "bean name must not be null");
        beandefinitions.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        Objects.requireNonNull(beanName, "bean name can not be null");
        BeanDefinition bd = beandefinitions.get(beanName);
        return bd;
    }

    private Object createBean(String beanName, BeanDefinition bd, Object args) {
        Object bean = ClassUtils.createBeanByConstructor(bd.getClassName(), null);


        return bean;
    }

    private Class<?> resolveBeanClass(final BeanDefinition bd) throws ClassNotFoundException {
        ClassLoader clToUse = getBeanClassLoader();
        String className = bd.getClassName();
        if (className != null) {
            return ClassUtils.forName(className, clToUse);
        }
        return null;
    }

    private ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }
}
