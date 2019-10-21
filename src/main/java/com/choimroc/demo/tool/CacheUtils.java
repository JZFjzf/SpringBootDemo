package com.choimroc.demo.tool;


import com.choimroc.demo.security.UserInfo;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * The type Cache utils.
 *
 * @author choimroc
 * @since 2019 /10/10
 */
@Component
public class CacheUtils extends RedisUtils {

    @Autowired
    public CacheUtils(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    /**
     * 放入用户信息,有效期为7天
     *
     * @param userId   the user id
     * @param userInfo the userInfo
     * @return the token
     */
    public boolean setToken(Long userId, UserInfo userInfo) {
        String key = String.valueOf(userId);
        String userCache = new Gson().toJson(userInfo);
        return set(key, userCache, 7, TimeUnit.DAYS);
    }

    /**
     * Gets token.
     *
     * @param userId the user id
     * @return the token
     */
    public UserInfo getToken(Long userId) {
        String userCache = get(String.valueOf(userId));
        if (userCache == null) {
            return null;
        }
        return new Gson().fromJson(userCache, UserInfo.class);
    }

    /**
     * 刷新token过期时间
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean refreshToken(Long userId) {
        return expire(String.valueOf(userId), 7, TimeUnit.DAYS);
    }
}
