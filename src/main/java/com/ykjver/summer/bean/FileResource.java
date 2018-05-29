package com.ykjver.summer.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author yk
 * @date 2018/5/29
 */
public class FileResource implements Resource{

    @Override
    public InputStream load() {
        URL resource = FileResource.class.getClassLoader().getResource("bean.xml");
        try {
            return new FileInputStream(resource.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
