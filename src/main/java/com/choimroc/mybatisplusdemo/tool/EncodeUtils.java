package com.choimroc.mybatisplusdemo.tool;

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

    public static String generateMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String generateSaltMD5(String str) {
        String base = str + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static String generateBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String base64Encode(String str) {
        return new String(Base64.getDecoder().decode(str));
    }

    public static String generateToken(int userId) {
        String id = String.valueOf(userId);
        String token = generateSaltMD5(id + System.currentTimeMillis());
        return generateBase64(id) + "." + generateBase64(token);
    }

    public static String generatePassword(String password) {
        return generateSaltMD5(password);
    }

    public static String generateInvitationCode(int teamId) {
        return generateSaltMD5(String.valueOf(teamId) + System.currentTimeMillis());
    }
}
