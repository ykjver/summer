package com.ykjver.summer.bean;

import com.ykjver.summer.core.ClassUtils;
import com.ykjver.summer.core.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ykjver@gmail.com
 * @date 2018/5/29
 */
public class XmlBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beandefinitions = new HashMap<>(256);

    private final Map<String, Object> singletonObjects = new HashMap<>(256);

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String name) throws RuntimeException {
        Object bean = null;
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            return sharedInstance;
        }
        final BeanDefinition bd = getBeanDefinition(name);
        String[] dependsOn = bd.getDependsOn();
        if (dependsOn != null) {
            for (String dep : dependsOn) {
                getBean(dep);
            }
        }

        if(bd.isSingleton()) {
            bean = createBean(name, bd, null);
            populateBean(bean, bd);
        }
        singletonObjects.put(name, bean);
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

    public BeanDefinition getBeanDefinition(String beanName) {
        Objects.requireNonNull(beanName, "bean name can not be null");
        BeanDefinition bd = beandefinitions.get(beanName);
        return bd;
    }

    private Object createBean(String beanName, BeanDefinition bd, Object args) {
        Class<?> clazz = null;
        try {
            clazz = resolveBeanClass(bd);
            Constructor<?> constructorToUser = clazz.getDeclaredConstructor();
            return constructorToUser.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return clazz;
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
