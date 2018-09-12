package com.ykjver.summer.core;

import com.ykjver.summer.Main;
import com.ykjver.summer.bean.BeanDefinition;
import com.ykjver.summer.bean.ClassPathResource;
import com.ykjver.summer.bean.exception.CreateBeanException;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * starter of summer
 *
 * @author yk
 * @date 2018/9/12
 */
public class Summer {
    public static void main(String[] args) {
        run(Main.class);
    }

    public static void run(Class kclass) {
        String filePath = kclass.getClassLoader().getResource("").getFile();
        filePath = filePath.replace('\\', File.separatorChar);
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        System.out.println(filePath);
        File appDir = new File(filePath);
        List<Class> candidateList = new ArrayList<>();
        load(appDir, candidateList, filePath);
    }

    public static void load(File appDir, List<Class> candidateList, String batchPath) {
        File[] beanDirs = appDir.listFiles();
        if(beanDirs == null) {
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
                    Class<?> klass = Class.forName(filePath);
//                    if (klass != null || klass.isAnnotationPresent()) {
//                        candidateList.add(klass);
//                    }
                    System.out.println("file: " + filePath);
                }
            }
        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
