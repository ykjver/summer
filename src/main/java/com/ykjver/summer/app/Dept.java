package com.ykjver.summer.app;

import com.ykjver.summer.bean.anno.Bean;

/**
 * @author ykjver@gmail.com
 * @date 2018/6/2
 */
@Bean
public class Dept {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
