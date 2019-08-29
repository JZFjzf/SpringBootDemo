package com.choimroc.demo.tool;

/**
 * @author choimroc
 * @since 2019/3/9
 */
public class StringUtils {

    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || "null".equals(str);
    }

    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }
}
