package com.ykjver.summer.core;

import sun.jvm.hotspot.debugger.Page;

import java.lang.reflect.Field;

/**
 * @author ykjver@gmail.com
 * @date 2018/6/2
 */
public class ReflectUtils {
    public static void setFiledValue(Object bean, String propertyName, String propertyValue) {
        try {
            Class<?> clazz = bean.getClass();
            Field declaredField = clazz.getDeclaredField(propertyName);
            if (declaredField != null) {
                declaredField.setAccessible(true);
                declaredField.set(bean, propertyValue);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
