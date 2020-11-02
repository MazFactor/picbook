package com.lyl.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class DateUtil {
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 私有构造函数，不允许实例化
     */
    private DateUtil() {
    }

    /**
     * 默认时区类型：东八区
     */
    public static final String DEFAULT_TIME_ZONE_TYPE = "GMT+8";
    /**
     * 默认日期和时间格式
     */
    public static final String DEFAULT_FORMAT_PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_FORMAT_PATTERN_DATE = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_FORMAT_PATTERN_TIME = "HH:mm:ss";

    /**
     * 日期和时间格式一："yyyyMMddHHmmss"
     */
    public static final String FORMAT_PATTERN_DATETIME_ONE = "yyyyMMddHHmmss";
    /**
     * 日期和时间格式二："yyyy/M/d HH:mm"
     */
    public static final String FORMAT_PATTERN_DATETIME_TWO = "yyyy/M/d HH:mm";
    /**
     * 日期格式一："yyyyMMdd"
     */
    public static final String FORMAT_PATTERN_DATE_ONE = "yyyyMMdd";
    /**
     * 日期格式二："yyyy/M/d"
     */
    public static final String FORMAT_PATTERN_DATE_TWO = "yyyy/M/d";
    /**
     * 时间格式一："HHmmss"
     */
    public static final String FORMAT_PATTERN_TIME_ONE = "HHmmss";

    /**
     * 获取当前系统日期和时间
     *
     * @return 当前系统日期和时间
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取指定格式的当前系统日期和时间的字符串
     *
     * @param pattern 日期和时间格式，若为空则使用默认的格式
     * @return 指定格式的当前系统日期和时间的字符串
     */
    public static String getFormatCurrentDate(String pattern) {
        if (StringUtil.isNullOrWhiteSpace(pattern)) {
            pattern = DEFAULT_FORMAT_PATTERN_DATE;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(getCurrentDate());
    }

    /**
     * 将指定日期初始化为日历
     *
     * @param date 日期
     * @return 日历
     */
    private static Calendar initCalendar(Date date) {
        Calendar temCalendar = Calendar.getInstance();
        temCalendar.setTime(date);
        return temCalendar;
    }

    /**
     * 获取指定日期当月的月底日期
     *
     * @param date 日期
     * @return 月底日期
     */
    public static Date getEndOfMonth(Date date) {
        Calendar temCalendar = initCalendar(date);
        temCalendar.set(Calendar.DAY_OF_MONTH, temCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return temCalendar.getTime();
    }

    /**
     * 计算加指定天数后的日期
     *
     * @param date 日期
     * @param days 天数
     * @return 指定天数后的日期
     */
    public static Date afterDay(Date date, int days) {
        Calendar temCalendar = initCalendar(date);
        temCalendar.add(Calendar.DATE, days);
        return temCalendar.getTime();
    }

    /**
     * 计算加指定分钟后的时间
     *
     * @param date 时间
     * @param minute 分钟
     * @return 指定分钟后的时间
     */
    public static Date afterMinute(Date date, int minute) {
        Calendar temCalendar = initCalendar(date);
        temCalendar.add(Calendar.MINUTE, minute);
        return temCalendar.getTime();
    }

    /**
     * 计算加指定秒钟后的时间
     *
     * @param date 时间
     * @param second 秒钟
     * @return 指定秒钟后的时间
     */
    public static Date afterSecond(Date date, int second) {
        Calendar temCalendar = initCalendar(date);
        temCalendar.add(Calendar.SECOND, second);
        return temCalendar.getTime();
    }

    /**
     * 将日期格式的字符串转换为日期
     *
     * @param dateStr 字符串
     * @param dateFormat 日期格式
     * @return 日期
     */
    public static Date dateStrToDate(String dateStr, String dateFormat) {
        if (StringUtil.isNullOrWhiteSpace(dateStr)) {
            return null;
        }
        if (StringUtil.isNullOrWhiteSpace(dateFormat)) {
            dateFormat = DEFAULT_FORMAT_PATTERN_DATETIME;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param dateStr 日期格式字符串
     * @param dateFormat 日期格式
     * @return 长整型时间戳
     */
    public static long dateStrToLong(String dateStr, String dateFormat) {
        Date date = dateStrToDate(dateStr, dateFormat);
        return date == null ? 0L : date.getTime();
    }

    /**
     * 将日期转换为字符串
     *
     * @param date 日期
     * @param dateFormat 日期格式
     * @return 字符串
     */
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 获取一个日期类型的年月日
     *
     * @param date 日期
     * @return 只包含年月日的日期
     */
    public static Date getDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN_DATE);
        try {
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取两个日期相差天数
     *
     * @param start 起始日期
     * @param end 截止日期
     * @return 相差天数
     */
    public static long getDatePeriod(Date start, Date end) {
        Date startDay = getDay(start);
        Date endDay = getDay(end);
        if (startDay == null || endDay == null) {
            return -1L;
        }
        return Math.abs((startDay.getTime() - endDay.getTime()) / (1000L * 60L * 60L * 24L));
    }

    /**
     * 获取两个时间的间隔
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 返回相差毫秒数
     */
    public static long getTimePeriod(Date start, Date end) {
        return end.getTime() - start.getTime();
    }

    /**
     * 获取两个时间的间隔
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 返回相差分钟数
     */
    public static long getMinutePeriod(Date start, Date end) {
        //获得这两个时间的毫秒值
        long diff = end.getTime() - start.getTime();
        ////此处用毫秒值除以分钟再除以毫秒既得两个时间相差的分钟数
        long minute = diff/60/1000;
        return minute;
    }

    /**
     * 获取当前时间前一天的日期
     * @return
     */
    public static String getBeforeOneDayCurrentTime(){
        //DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN_DATE);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 比较两个时间（时分秒）的大小
     * @param time1
     * @param time2
     * @return true:time1>time2 false:time1<time2
     */
    public static boolean compTime(String time1,String time2){
        try {
            if (time1.indexOf(":")<0||time2.indexOf(":")<0) {
                System.out.println("格式不正确");
            }else{
                String[]array1 = time1.split(":");
                int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60+Integer.valueOf(array1[2]);
                String[]array2 = time2.split(":");
                int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60+Integer.valueOf(array2[2]);
                return total1-total2>0?true:false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;

    }
}
