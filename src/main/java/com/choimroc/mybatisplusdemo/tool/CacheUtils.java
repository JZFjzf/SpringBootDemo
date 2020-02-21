package com.choimroc.mybatisplusdemo.tool;


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
     * @param token the userInfo
     * @return the token
     */
    public boolean setToken(Long userId, String token) {
        String key = String.valueOf(userId);
        return set(key, token, 7, TimeUnit.DAYS);
    }

    /**
     * Gets token.
     *
     * @param userId the user id
     * @return the token
     */
    public String getToken(Long userId) {
        return get(String.valueOf(userId));
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
