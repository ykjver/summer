package com.ykjver.summer.core;

import com.ykjver.summer.bean.exception.CreateBeanException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ykjver@gmail.com
 * @date 2018/5/29
 */
public class ClassUtils {

    private static final Map<String, Class<?>> commonClassCache = new HashMap<>(64);

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {

        }
        if (cl == null) {
            try {
                cl = ClassUtils.class.getClassLoader();
            } catch (Throwable ex) {

            }
        }
        return cl;
    }

    public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> clazz = commonClassCache.get(name);
        if (clazz != null) {
            return clazz;
        }
        ClassLoader clToUse = classLoader;
        if (clToUse == null) {
            clToUse = getDefaultClassLoader();
        }
        try {
            return (clToUse != null ? clToUse.loadClass(name) : Class.forName(name));
        } catch (ClassNotFoundException ex) {
            throw ex;
        }
    }


    public static Object createBeanByConstructor(String className, Class[] args) {
        Class<?> klass = null;
        try {
            klass = forName(className, getDefaultClassLoader());
            return klass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new CreateBeanException("instantiate class fail, class name: " + className);
        }
    }
}
