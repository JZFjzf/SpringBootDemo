package com.choimroc.demo.tool;

/**
 * 编号生成工具
 *
 * @author choimroc
 * @since 2019/5/2
 */
public class IdentifierUtils {
    //上次生成编号的时间截
    private long cLastTimestamp = -1L;
    private static IdentifierUtils identifierUtils;

    public static IdentifierUtils getInstance() {
        if (identifierUtils == null) {
            identifierUtils = new IdentifierUtils();
        }
        return identifierUtils;
    }

    private long timeGen() {
        return System.currentTimeMillis();
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

    public synchronized String generateCUSNum() {
        long timestamp = getTimestamp(cLastTimestamp);
        // 上次生成 ID 的时间截
        cLastTimestamp = timestamp;
        return "CUS" + timestamp;
    }
}
