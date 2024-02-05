package com.unknown.common.utils;

import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Created Guoqz
 * @Date 2023-09-15 23:51
 * @Description 日期时间工具类
 */
public class DateTimeUtil {

    public static void main(String[] args) {
        List<String> dateListByYear = getDateListByYear(null);
        for (String s : dateListByYear) {
            System.out.println(s);
        }
    }

    /**
     * 计算指定年份的所有日期
     *
     * @param year
     * @return java.util.List<java.lang.String>  yyyy-MM-dd
     */
    public static List<String> getDateListByYear(Integer year) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> allDateList = new ArrayList<>();
        if (null == year) {
            year = Integer.valueOf(getYear());
        }
        LocalDate endDate = LocalDate.of(year, 1, 1);
        while (year == endDate.getYear()) {
            allDateList.add(endDate.format(formatter));
            endDate = endDate.plusDays(1L);
        }
        return allDateList;
    }

    /**
     * 获取当前时间是周几
     *
     * @return 星期一 ...
     */
    public static String getWeekCal() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[dayOfWeek];
    }


    public static String getWeek(String date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        try {
            currentDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        return simpleDateFormat.format(currentDate);
    }

    public static String getWeek() {
        return getWeek(getDate());
    }


    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getMillFormat(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(timestamp);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestamp);
    }

    /**
     * HH:mm:ss
     *
     * @param timestamp 时间戳
     * @return HH:mm:ss
     */
    public static String getTimeForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(timestamp);
    }

    /**
     * HH:mm:ss.SSS
     *
     * @param timestamp 时间戳
     * @return HH:mm:ss.SSS
     */
    public static String getTimeMillForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
        return format.format(timestamp);
    }

    /**
     * yyyy-MM-dd
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd
     */
    public static String getDateForForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(timestamp);
    }

    /**
     * yyyy-MM
     *
     * @param timestamp 时间戳
     * @return yyyy-MM
     */
    private static String getYearMonthForForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(timestamp);
    }


    /**
     * yyyy
     *
     * @param timestamp 时间戳
     * @return yyyy
     */
    private static String getYearForForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(timestamp);
    }

    /**
     * MM
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd
     */
    private static String getMonthForForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(timestamp);
    }

    /**
     * dd
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd
     */
    private static String getDayForForTimestamp(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(timestamp);
    }

    /**
     * 获取毫秒级格式化时间
     *
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getMillFormat() {
        return getMillFormat(System.currentTimeMillis());
    }

    /**
     * yyyy
     *
     * @return yyyy
     */
    public static String getYear() {
        return getYearForForTimestamp(System.currentTimeMillis());
    }

    /**
     * MM
     *
     * @return MM
     */
    public static String getMonth() {
        return getMonthForForTimestamp(System.currentTimeMillis());
    }

    /**
     * dd
     *
     * @return dd
     */
    public static String getDay() {
        return getDayForForTimestamp(System.currentTimeMillis());
    }

    /**
     * yyyy-MM
     *
     * @return dd
     */
    public static String getYearMonth() {
        return getYearMonthForForTimestamp(System.currentTimeMillis());
    }


    // ----------


    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        return DateUtil.today();
    }


    /**
     * HH:mm:ss
     *
     * @return
     */
    public static String getTime() {
        Date date = DateUtil.date(Calendar.getInstance());
        DateFormat sfd = new SimpleDateFormat("HH:mm:ss");
        return sfd.format(date);
    }

    /**
     * 将日期字符串转换为DateTime对象，格式：
     * <p>
     * yyyy-MM-dd HH:mm:ss
     * yyyy/MM/dd HH:mm:ss
     * yyyy.MM.dd HH:mm:ss
     * yyyy年MM月dd日 HH时mm分ss秒
     * yyyy-MM-dd
     * yyyy/MM/dd
     * yyyy.MM.dd
     * HH:mm:ss
     * HH时mm分ss秒
     * yyyy-MM-dd HH:mm
     * yyyy-MM-dd HH:mm:ss.SSS
     * yyyy-MM-dd HH:mm:ss.SSSSSS
     * yyyyMMddHHmmss
     * yyyyMMddHHmmssSSS
     * yyyyMMdd
     * EEE, dd MMM yyyy HH:mm:ss z
     * EEE MMM dd HH:mm:ss zzz yyyy
     * yyyy-MM-dd'T'HH:mm:ss'Z'
     * yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * yyyy-MM-dd'T'HH:mm:ssZ
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ
     *
     * @param dateTime 以上
     * @return DateTime.class
     */

    public static Date parserDateTimeStr(String dateTime) {
        return DateUtil.parse(dateTime);
    }


    /**
     * 日期 转 年龄
     *
     * @param birthday yyyy-MM-dd
     * @return
     */
    public static Integer toAge(String birthday) {
        if (null == birthday || "".equals(birthday)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(birthday);
        LocalDate now = LocalDate.now();
        Period period = Period.between(localDate, now);
        return period.getYears();
    }

}
