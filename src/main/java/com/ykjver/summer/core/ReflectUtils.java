package com.ykjver.summer.core;

import com.ykjver.summer.bean.exception.CreateBeanException;
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
            declaredField.setAccessible(true);
            if (declaredField.getType() == Integer.class
                    || declaredField.getType().getName().equals("int")) {
                declaredField.setInt(bean, Integer.valueOf(propertyValue));
            } else {
                declaredField.setAccessible(true);
                declaredField.set(bean, propertyValue);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldRefValue(Object bean, String fieldName, Object fieldVal) {
        if (fieldName == null || fieldName.isEmpty()) {
            throw new CreateBeanException("fieldName can't be empty");
        }
        try {
            Field field;
            field = bean.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(bean, fieldVal);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CreateBeanException(e);
        }
    }
}
