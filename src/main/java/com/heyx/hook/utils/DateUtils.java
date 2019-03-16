package com.heyx.hook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public final static String start = "start";
    public final static String end = "end";

    /**
     * 对日期进行加减，天
     *
     * @param date 待处理日期
     * @param n    加减天数
     * @return 时间
     */
    public static Date addDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, n);
        return calendar.getTime();
    }

    /**
     * 获取指定时间所在天的开始和结束时间
     */
    public static Date getDay(Date date, String status) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (start.equals(status)) {
            calendar.set(year, month, day, 0, 0, 0);
        } else {
            calendar.set(year, month, day, 23, 59, 59);
        }
        return calendar.getTime();
    }

    /**
     * 获取指定时间所在周的开始和结束天
     */
    public static Date getWeekDay(Date date, String status) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        if (start.equals(status)) {
            return getDay(cal.getTime(), start);
        } else {
            cal.add(Calendar.DAY_OF_WEEK, 6);
            return getDay(cal.getTime(), end);
        }
    }

    /**
     * 获取指定时间所在月的开始和结束天
     */
    public static Date getMonthDay(Date date, String status) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = 0;
        if (start.equals(status)) {
            last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, last);
            return getDay(cal.getTime(), start);
        } else {
            last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, last);
            return getDay(cal.getTime(), end);
        }
    }

    /**
     * 获取指定时间所在年的开始和结束天
     */
    public static Date getYearDay(Date date, String status) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = 0;
        if (start.equals(status)) {
            last = cal.getActualMinimum(Calendar.DAY_OF_YEAR);
            cal.set(Calendar.DAY_OF_YEAR, last);
            return getDay(cal.getTime(), start);
        } else {
            last = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
            cal.set(Calendar.DAY_OF_YEAR, last);
            return getDay(cal.getTime(), end);
        }

    }

    /**
     * 获取指定时间所在季度的开始和结束天
     */
    public static Date getQuarterDay(Date date, String status) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        if (start.equals(status)) {
            if (currentMonth >= 1 && currentMonth <= 3) {
                cal.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                cal.set(Calendar.MONTH, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                cal.set(Calendar.MONTH, 6);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                cal.set(Calendar.MONTH, 9);
            }
            cal.set(Calendar.DATE, 1);
            return getDay(cal.getTime(), start);
        } else {
            if (currentMonth >= 1 && currentMonth <= 3) {
                cal.set(Calendar.MONTH, 2);
                cal.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                cal.set(Calendar.MONTH, 5);
                cal.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                cal.set(Calendar.MONTH, 8);
                cal.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                cal.set(Calendar.MONTH, 11);
                cal.set(Calendar.DATE, 31);
            }
            return getDay(cal.getTime(), end);
        }
    }

    /**
     * 获取时间年月日
     */
    public static String toDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) ;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取日期的年月日
     *
     * @param calend 1.年；2.月；5.日
     */
    public static int splitDate(Date date, int calend) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int d = calendar.get(calend);
        if (calend == 2) {
            d++;
        }
        return d;
    }

    public static String toMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return year + "年" + month + "月";
    }

    /**
     * 判断日期格式是否正确
     */
    public static boolean isNotFormated(String datString, String pattern) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(datString.trim());
        } catch (ParseException ignored) {
        }
        return null == date;
    }

    public static boolean isEqules(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str1 = sdf.format(startDate);
        String str2 = sdf.format(endDate);
        return str1.equals(str2);
    }

    public static int daysBetween(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date convertDate(String datString, String pattern) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(datString.trim());
        } catch (ParseException e) {
        }
        return date;
    }

    public static String convertString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String convertString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 日期转换函数
     */
    public static Date convertDate(String datString) {
        Date date = null;
        if (null != datString) {
            //格式八
            SimpleDateFormat sdfe = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
            try {
                date = sdfe.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式六
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式七
            SimpleDateFormat sdg = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                date = sdg.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式三
            SimpleDateFormat sdc = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdc.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }

            SimpleDateFormat sdct = new SimpleDateFormat("yyyy年MM月dd日");
            try {
                date = sdct.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式五
            SimpleDateFormat sde = new SimpleDateFormat("yyyy/MM/dd");
            try {
                date = sde.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式二
            SimpleDateFormat sdb = new SimpleDateFormat("yyyy-MM");
            try {
                date = sdb.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            SimpleDateFormat sdbt = new SimpleDateFormat("yyyy年MM月");
            try {
                date = sdbt.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式四
            SimpleDateFormat sdd = new SimpleDateFormat("yyyy/MM");
            try {
                date = sdd.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }
            // 格式一
            SimpleDateFormat sda = new SimpleDateFormat("yyyy");
            try {
                date = sda.parse(datString.trim());
                return date;
            } catch (ParseException e) {
            }

        }
        return date;
    }

}
