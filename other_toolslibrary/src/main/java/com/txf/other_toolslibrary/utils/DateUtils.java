package com.txf.other_toolslibrary.utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author txf
 * @Title
 * @package com.simpleframe.utils
 * @date 2016/11/2 0002
 */

public class DateUtils {

    public static final String PATTERN_MONTH = "M";//月
    public static final String PATTERN_DAY = "d";//天
    public static final String PATTERN_WEEK = "E";//星期
    public static final String PATTERN_DATE_STRING = "yyyy-MM-dd";//年-月-日

//    HH 小时（00 至 23），显示为两位十进制数。
//    mm 分钟（00 至 59），显示为两位十进制数。
//    ss 秒数（00 至 61），显示为两位十进制数。 60,61 是因为有"润秒"
    /**
     * @param millisecond 时间毫秒值
     * @param type        -1 == 上一个月, 1 == 下一个月, 0 == 当前时间
     * @param day         几号  最后一号的毫秒值传 -1
     * @return 毫秒值
     */
    public static long getLastOrNextOrCurrent(long millisecond, int type, int day) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date(getMillisecond(formatDate(millisecond, PATTERN_DATE_STRING))));
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + type);
        int maxday = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_MONTH, day == -1 ? maxday : day);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * @param date 日期字符串 传入的日期字符串格式 :"年-月-日"
     * @return 返回日期的毫秒值
     */
    public static long getMillisecond(String date) {
        SimpleDateFormat sf = new SimpleDateFormat(PATTERN_DATE_STRING);
        Date d = null;
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * @param date 日期字符串 传入的日期字符串格式 :"年-月-日"
     * @return 返回日期的毫秒值
     */
    public static long getMillisecond(String date, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
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
     *
     * @param millisecond 毫秒值
     * @param pattern     格式化模式
     */
    public static String formatDate(long millisecond, String pattern) {
        Date now = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(now);
    }

    /**
     * @param millisecond 日期 毫秒值
     * @return 返回日期月份的1号是星期几?
     */
    public static int getWeek(long millisecond) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date(millisecond));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param millisecond 日期 毫秒值
     * @return 返回日期是星期几?
     */
    public static String getCurrentWeek(long millisecond) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date(millisecond));
        String week = "";
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * @param millisecond 毫秒值
     * @return 返回日期月份的最大天数
     */
    public static int getDayOfMonth(long millisecond) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date(millisecond));
        int day = calendar.getActualMaximum(Calendar.DATE);
        return day;
    }

    public static int getDayOfMonth(String data) {
        return getDayOfMonth(getMillisecond(data));
    }

    /**
     * @param date 判断日期格式是否是  pattern  例 ; yyyy-MM-dd, "年-月-日"
     * @return 如果不是则返回 fase
     */
    public static boolean isValidDate(String date, String pattern) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
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
     * @param millisecond1
     * @param millisecond2 判断两个时间是否是同一天
     */
    public static boolean isSameDate(long millisecond1, long millisecond2) {
        return isSameDate(new Date(millisecond1), new Date(millisecond2));
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }


    /**
     * 获取当前的日期时间(网络时间)
     */
    public static String getWebsiteDate(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.ntsc.ac.cn");// 取得资源对象
                    URLConnection uc = url.openConnection();// 生成连接对象
                    uc.connect();// 发出连接
                    long ld = uc.getDate();// 读取网站日期时间
                    Date date = new Date(ld);// 转换为标准时间对象
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// 输出北京时间
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = sdf.format(date);
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    handler.sendEmptyMessage(-1);
                } catch (IOException e) {
                    handler.sendEmptyMessage(-2);
                }
            }
        }).start();
        return null;
    }
}
