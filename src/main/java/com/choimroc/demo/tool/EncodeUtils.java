package com.choimroc.demo.tool;

import org.springframework.util.DigestUtils;

import java.util.Base64;

/**
 * 基于spring 编码
 *
 * @author choimroc
 * @since 2019/4/16
 */
public class EncodeUtils {
    private static final String salt = "*#&%$^";

    public static String createMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String createSaltMD5(String str) {
        String base = str + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static String createToken(int id) {
        String token = createSaltMD5(String.valueOf(id)) + createSaltMD5(String.valueOf(System.currentTimeMillis()));
        return Base64.getMimeEncoder().encodeToString(token.getBytes());

    }
}
