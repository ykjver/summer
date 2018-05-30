package com.ykjver.summer.bean;

import com.ykjver.summer.core.ClassUtils;

import java.io.InputStream;
import java.util.Objects;

/**
 * @author ykjver@gmail.com
 * @date 2018/5/29
 */
public class ClassPathResource implements Resource{

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        Objects.requireNonNull(path, "path must not be null");
        this.path = path;
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }
}
