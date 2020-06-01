package com.donkingliang.imageselector.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getImageTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        if (sameDay(calendar, imageTime)) {
            return "今天";
        } else if (sameWeek(calendar, imageTime)) {
            return "本周";
        } else if (sameMonth(calendar, imageTime)) {
            return "本月";
        } else {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
            return sdf.format(date);
        }
    }

    public static boolean sameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean sameWeek(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.WEEK_OF_YEAR) == calendar2.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean sameMonth(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * @param format_ 日期格式 例 yyyy-MM-dd
     * @param date    判断 日期格式是否是 format_
     * @return 如果不是则返回 fase
     */
    public static boolean isValidDate(String date, String format_) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(format_);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * @param date   日期字符串
     * @param format 传入的日期字符串格式
     * @return 返回日期的毫秒值
     */
    public static long getMillisecond(String date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * 日期格式化
     */
    public static String formatDate(String date, String format1, String format2) {
        Date now = new Date(getMillisecond(date, format1));
        SimpleDateFormat format = new SimpleDateFormat(format2);
        return format.format(now);
    }

    /**
     * 日期格式化
     *
     * @param millisecond 毫秒值
     * @param pattern     格式化模式
     */
    public static String formatDate(long millisecond, String pattern) {
        Date now = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(now);
    }
}
