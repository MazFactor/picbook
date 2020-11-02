package com.lyl.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 字符串工具
 *
 * @author dcc
 * @date 2019/9/2
 */
public class NormalUtils {

    /**
     * 判空操作
     *
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value) || "".equals(value.trim());
    }

    public static boolean isBlankAtLeastOne(Object... values) {
        for (Object value : values) {
            if (value == null) {
                return true;
            }
            if (value instanceof String) {
                if (isBlank((String) value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * int or long to long
     * @param o
     * @return
     */

    public static  Long parseLongFromObject(Object o){
        if(o instanceof  Integer){
            Integer i = (Integer)o;
            Long l = i.longValue();
            return l;
        }
        return (Long)o;
    }

    public static Date addTimeWithMicroSecs(Date date, Long microSecs) {
        return new Date(date.getTime() + microSecs);
    }

    public static Date addTimeWithSecs(Date date, Long secs) {
        return addTimeWithMicroSecs(date, secs * 1000);
    }

    public static Date addTimeWithDay(Date date, Long day) {
        return addTimeWithSecs(date, day * 3600 * 24);
    }

    public static Integer convert(Long l) {
        if (l == null) {
            return null;
        }
        if (l > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return l.intValue();

    }

    public static boolean isNull(List list){
        if(list==null||list.isEmpty()){
            return true;
        }
        return false;
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINESE);
        return sdf.format(date);
    }

}
