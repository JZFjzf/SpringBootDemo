package com.choimroc.demo.tools;

/**
 * @author choimroc
 * @since 2019/3/9
 */
public class StringUtils {

    private static boolean isBlank(String str) {
        return str == null || str.isEmpty() || str.replaceAll(" ", "").isEmpty() || "null".equals(str);
    }

    public static boolean isBlank(String... strings) {
        for (String str : strings) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }
}
