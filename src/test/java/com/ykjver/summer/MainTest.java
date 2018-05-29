package com.ykjver.summer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yk
 * @date 2018/5/29
 */
public class MainTest {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("summer");
        list.add("winter");
        list.add("spring");
        for (int i = 0; i < 10; i++) {
            list.add("spring");
        }
        System.out.println(list.size());

        Set<String> set = new HashSet<>();
        set.add("summer");
        set.add("spring");
        set.add("autumn");
        System.out.println(list.containsAll(set));
        System.out.println(set.size());
    }
}
