package com.choimroc.demo.tool;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Date time utils.
 *
 * @author choimroc
 * @since 2019 /11/22
 */
public class DateTimeUtils {
    private static final String DEFAULT_DATETIME_FORMATS = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMATS = "yyyy-MM-dd";

    /**
     * Millis time long.
     *
     * @return the long
     */
    public static long millisTime() {
        return Clock.systemDefaultZone().millis();
    }

    /**
     * Second time long.
     *
     * @return the long
     */
    public static long secondTime() {
        return Clock.systemDefaultZone().millis() / 1000;
    }


    /**
     * Default date time string.
     *
     * @return the string
     */
    public static String nowDateTime() {
        return formatDateTime(LocalDateTime.now());
    }


    /**
     * Default date string.
     *
     * @return the string
     */
    public static String nowDate() {
        return formatDate(LocalDate.now());
    }

    /**
     * Format date time string.
     *
     * @param localDateTime the local date time
     * @param format        the format
     * @return the string
     */
    public static String formatDateTime(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Format date time to yyyy-MM-dd HH:mm:ss.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return formatDateTime(localDateTime,DEFAULT_DATETIME_FORMATS);
    }

    /**
     * Parse date time local date time.
     *
     * @param datetime the datetime
     * @param format   the format
     * @return the local date time
     */
    public static LocalDateTime parseDateTime(String datetime, String format) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(format));
    }

    /**
     * parse date time yyyy-MM-dd HH:mm:ss.
     *
     * @param datetime the datetime
     * @return the local date time
     */
    public static LocalDateTime parseDateTime(String datetime) {
        return parseDateTime(datetime, DEFAULT_DATETIME_FORMATS);
    }


    /**
     * Format date string.
     *
     * @param localDate the local date
     * @param format    the format
     * @return the string
     */
    public static String formatDate(LocalDate localDate, String format) {
        return localDate.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Format date time to yyyy-MM-dd.
     *
     * @param localDate the local date
     * @return the string
     */
    public static String formatDate(LocalDate localDate) {
        return formatDate(localDate,DEFAULT_DATE_FORMATS);
    }

    /**
     * Parse date local date time.
     *
     * @param date   the date
     * @param format the format
     * @return the local date time
     */
    public static LocalDate parseDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * parse date time yyyy-MM-dd.
     *
     * @param date the datetime
     * @return the local date time
     */
    public static LocalDate parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMATS);
    }
}
