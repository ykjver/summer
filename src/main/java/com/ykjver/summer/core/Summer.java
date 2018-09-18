package com.ykjver.summer.core;

import com.ykjver.summer.Main;
import com.ykjver.summer.bean.BeanDefinition;
import com.ykjver.summer.bean.BeanDefinitionRegistry;
import com.ykjver.summer.bean.BeanFactory;
import com.ykjver.summer.bean.XmlBeanFactory;
import com.ykjver.summer.bean.anno.Bean;
import com.ykjver.summer.bean.anno.Inject;
import com.ykjver.summer.bean.exception.CreateBeanException;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * starter of summer
 *
 * @author yk
 * @date 2018/9/12
 */
public class Summer {
    private BeanFactory beanFactory;

    public static void main(String[] args) {
        Summer summer = new Summer();
        summer.run(Main.class);
    }

    public  void run(Class kclass) {
        XmlBeanFactory beanFactory = new XmlBeanFactory();
        this.beanFactory = beanFactory;
        refresh(kclass, beanFactory);
        System.out.println(beanFactory.getBean("student"));
        System.out.println(beanFactory.getBean("dept"));
    }

    private void refresh(Class resource, BeanDefinitionRegistry beanDefinitionRegistry) {
        String filePath = resource.getClassLoader().getResource("").getFile();
        List<Class> candidateList = getCandidateList(filePath);
        if (candidateList != null && !candidateList.isEmpty()) {
            for (Class candidate : candidateList) {
                BeanDefinition beanDefinition = createBeanDefinition(candidate);
                beanDefinitionRegistry.registryBeanDefinition(beanDefinition.getName(), beanDefinition);
            }
        }
    }

    private BeanDefinition createBeanDefinition(Class klass) {
        Bean beanAnno = (Bean) klass.getDeclaredAnnotation(Bean.class);
        String beanName = beanAnno.name();
        if (beanName.isEmpty()) {
            char[] chars = klass.getSimpleName().toCharArray();
            if (chars[0] >= 65 && chars[0] <= 90) {
                chars[0] = (char)(chars[0] + 32);
                beanName = String.valueOf(chars);
            }
        }
        Field[] declaredFields = klass.getDeclaredFields();
        ArrayList<String> dependOnList = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            Inject inject = declaredField.getDeclaredAnnotation(Inject.class);
            if (inject != null) {
                String injectName = inject.name();
                if (StringUtils.isNotBlank(injectName)) {
                    dependOnList.add(injectName);
                } else {
                    String fieldName = declaredField.getName();
                    dependOnList.add(fieldName);
                }
            }
        }
        BeanDefinition bd = new BeanDefinition();
        bd.setName(beanName);
        bd.setClassName(klass.getName());
        bd.setDependsOn(dependOnList.toArray(new String[dependOnList.size()]));
        return bd;
    }

    private  List<Class> getCandidateList(String filePath) {
        filePath = filePath.replace('\\', File.separatorChar);
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        System.out.println(filePath);
        File appDir = new File(filePath);
        List<Class> candidateList = new ArrayList<>();
        load(appDir, candidateList, filePath);
        return candidateList;
    }


    public void load(File appDir, List<Class> candidateList, String batchPath) {
        File[] beanDirs = appDir.listFiles();
        if (beanDirs == null) {
            throw new CreateBeanException("app class directory is null");
        }
        try {

            for (File file : beanDirs) {
                if (file.isDirectory()) {
                    load(file, candidateList, batchPath);
                } else {
                    if (file.getName().endsWith(".class")) {
                        String filePath = file.getPath();
                        filePath = filePath.replace('\\', '/');
                        filePath = filePath.replace(batchPath, "");
                        filePath = filePath.replace('/', '.');
                        filePath = filePath.substring(0, filePath.lastIndexOf('.'));
                        Class<?> klass = Class.forName(filePath);
                        if (klass != null && klass.isAnnotationPresent(Bean.class)) {
                            candidateList.add(klass);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new CreateBeanException(e);
        }
    }
}
