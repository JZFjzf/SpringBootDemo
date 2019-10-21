package com.choimroc.demo.tool;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * 正则
 *
 * @author choimroc
 * @since 2019/10/21
 */
public final class RegexUtils {
    /**
     * 是否含有特殊字符
     */
    public static boolean isHasSpecial(String s) {
        String regex = "[`~!@#$%^&*()+=|{}':;,\\[\\]. <>/?！￥…（）—【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).find();
    }

    /**
     * 是否含有中文
     */
    public static boolean isHasChinese(String s) {
        String regex = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).find();
    }

    /**
     * 是否为正确手机号码(+86)
     */
    public static boolean isPhone(String s) {
        String regex = "^1\\d{10}$";
        return s.matches(regex);
    }

    /**
     * 是否为正确邮箱
     */
    public static boolean isEmail(String s) {
        String regex = "[[A-Za-z0-9]!#$%&'*+/=?^_`{|}~-]+(?:\\.[[A-Za-z0-9]!#$%&'*+/=?^_`{|}~-]+)*@(?:[[A-Za-z0-9_]](?:[[A-Za-z0-9_]-]*[[A-Za-z0-9_]])?\\.)+[\\w](?:[[A-Za-z0-9_]-]*[[A-Za-z0-9_]])?";
        return s.matches(regex);
    }

    /**
     * 是否为正确身份证
     */
    public static boolean isIDCard(String s) {
        String regex = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
        return s.matches(regex);
    }


    /**
     * 根据身份证号获取年龄（周岁）
     */
    public static int getAge(String s) {
        int year, month, day;
        if ((s.length() == 15 || s.length() == 18) && RegexUtils.isIDCard(s)) {
            year = Integer.parseInt(s.substring(6, 10));
            month = Integer.parseInt(s.substring(10, 12));
            day = Integer.parseInt(s.substring(12, 14));

            Calendar calendar = Calendar.getInstance();
            int nowYear = calendar.get(Calendar.YEAR);
            int nowMonth = calendar.get(Calendar.MONTH) + 1;
            int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
            //如果月份小于，或者月份相等但日数小于
            if (month < nowMonth || (month == nowMonth && day < nowDay)) {
                return nowYear - year - 1;
            }
            return nowYear - year;
        } else {
            return 19;
        }
    }

    /**
     * 是否为正确邮编（中国邮政）
     */
    public static boolean isZipCode(String s) {
        String regex = "[1-9]\\d{5}(?!\\d)";
        return s.matches(regex);
    }


}
