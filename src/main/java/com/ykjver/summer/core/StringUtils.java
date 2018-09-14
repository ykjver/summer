package com.ykjver.summer.core;

/**
 * @author yk
 * @date 2018/9/14
 */
public class StringUtils {

    public static boolean isBlank(String str) {
        return (str == null) || str.isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
