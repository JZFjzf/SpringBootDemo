package com.choimroc.demo.tool;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

/**
 * 编号生成工具(时间戳序列)
 *
 * @author choimroc
 * @since 2019/5/2
 */
public class SnUtils {
    /**
     * 上次生成的编号时间戳
     */
    private static Map<String, Long> last = new HashMap<>();

    static {
        //需要记录的编号
        last.put("key1", -1L);
        last.put("key2", -1L);
    }

    private static SnUtils snUtils;

    public static SnUtils getInstance() {
        if (snUtils == null) {
            snUtils = new SnUtils();
        }
        return snUtils;
    }

    private long timeGen() {
        return Clock.systemDefaultZone().millis();
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成 ID 的时间截
     * @return 当前时间戳(毫秒)
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();

        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }

        return timestamp;
    }

    private long getTimestamp(long lastTimestamp) {
        long timestamp = timeGen();

        // 如果当前时间小于上一次 ID 生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            timestamp = tilNextMillis(lastTimestamp);
        }
        return timestamp;
    }

    private String generate(String key) {
        long timestamp = getTimestamp(last.get(key));
        // 上次生成 ID 的时间截
        last.put(key, timestamp);
        return key + timestamp;
    }

    public String generateKey1() {
        synchronized ("key1") {
            return generate("key1");
        }
    }

    public synchronized String generateKey2() {
        synchronized ("key2") {
            return generate("key2");
        }
    }
}
