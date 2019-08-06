package com.choimroc.demo.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author choimroc
 * @since 2019/4/23
 */
@Component
public class RedisUtils {
    private StringRedisTemplate stringRedisTemplate;

    public RedisUtils() {
    }

    @Autowired
    public RedisUtils(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * 放入token,有效期为7天
     *
     * @param userId the user id
     * @param token  the token
     * @return the token
     */
    public boolean setToken(int userId, String token) {
        String key = String.valueOf(userId);
        return set(key, token, 7, TimeUnit.DAYS);
    }

    public String getToken(int userId) {
        return get(String.valueOf(userId));
    }

    /**
     * 刷新token过期时间
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean refreshToken(int userId) {
        return expire(String.valueOf(userId), 7, TimeUnit.DAYS);
    }


    public boolean setCode(String key,String code) {
        return set(key, code, 10, TimeUnit.MINUTES);
    }


    public String getCode(String key) {
        return get(key);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间
     * @return true成功 false 失败
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                stringRedisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean result = stringRedisTemplate.hasKey(key);
            return result != null && result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间 time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public boolean del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                Boolean result = stringRedisTemplate.delete(key[0]);
                return result != null && result;
            } else {
                Long aLong = stringRedisTemplate.delete(Arrays.asList(key));
                return aLong != null && aLong == key.length;
            }
        }
        return false;
    }
}
