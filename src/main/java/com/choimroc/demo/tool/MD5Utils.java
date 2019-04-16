package com.choimroc.demo.tool;

import org.springframework.util.DigestUtils;

/**
 * 基于spring
 *
 * @author choimroc
 * @since 2019/4/16
 */
public class MD5Utils {
    private static final String salt = "*#&%$^";

    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String getSaltMD5(String str) {
        String base = str + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
