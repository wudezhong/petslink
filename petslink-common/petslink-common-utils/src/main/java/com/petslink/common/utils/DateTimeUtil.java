package com.petslink.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author WuDezhong
 * @date 2019/7/31 10:24
 */
public class DateTimeUtil {

    // 日志格式
    private static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);

    // private static final MerlinLogger log =
    // MerlinLoggerFactory.getLogger(DBCommonSetUtil.class);

    // 获取系统日期
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return df.format(today);
    }

    // 获取当前系统时间 当前日期字符串，格式为："HH:mm:ss"
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return df.format(now);
    }

    /**
     * 获取当前系统时间
     *
     * @return 当前日期字符串，格式为："yyyy-MM-dd HH:mm:ss"
     * @author liucy
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        String todayStr = df.format(today);
        return todayStr;
    }

    /**
     * 获取当前时间字符串
     *
     * @return 当前时间字符串，格式为："yyyyMMddHHmmss"
     * @author liucy
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = new Date();
        String todayStr = df.format(today);
        return todayStr;
    }

    /**
     * 获取当前时间字符串
     *
     * @return 当前时间字符串，格式为："yyyy年MM月dd日"
     */
    public static String getCurrentDateFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        Date today = new Date();
        return df.format(today);
    }

    /**
     * 获取系统当前年度
     *
     * @return 当前日期字符串，格式为："yyyy" 例如：当前日期为2018年10月27日，则返回2018
     * @author wxn
     */
    public static String getCurrentYear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        Date today = new Date();
        String todayStr = df.format(today);
        return todayStr;
    }


    /**
     * 获取系统当前月份
     *
     * @return 当前日期字符串，格式为："MM" 例如：当前日期为2018年10月27日，则返回10
     * @author wxn
     */
    public static String getCurrentMonth() {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        Date today = new Date();
        String monthStr = df.format(today);
        return monthStr;
    }

    /**
     * 获取系统当前日期天数 dd
     *
     * @return 当前日期字符串，格式为："dd"。 例如：当前日期为2018年10月27日，则返回27
     * @author wxn
     */
    public static String getCurrentDay() {
        SimpleDateFormat df = new SimpleDateFormat("dd");
        Date today = new Date();
        String datStr = df.format(today);
        return datStr;
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            log.info("", e);
        }
        return days;
    }

    // 校验日期的合法性
    public static boolean isDate(String date) {
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        return mat.matches();
    }

    // 校验未来日期
    public static boolean checkFutureDate(String strDate) {
        // 判断是否为未来日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String nowdate = dateFormat.format(new Date());
        // String strdate = strDate.replace("/","-");
        return nowdate.compareTo(strDate) <= 0;
    }

    // 校验未来日期（不含当天）
    public static boolean checkFutureDateNotToday(String strDate) {
        // 判断是否为未来日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String nowdate = dateFormat.format(new Date());
        // String strdate = strDate.replace("/","-");
        return nowdate.compareTo(strDate) < 0;
    }

    /**
     * 获取周岁
     * 
     * @param birthday
     * @return
     */
    public static int getCurrentAge(String birthday, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar curr = Calendar.getInstance();
            Calendar born = Calendar.getInstance();
            curr.setTime(sdf.parse(date));
            born.setTime(sdf.parse(birthday));
            int age = curr.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (age <= 0) {
                return 0;
            }
            int currMonth = curr.get(Calendar.MONTH);
            int currDay = curr.get(Calendar.DAY_OF_MONTH);
            int bornMonth = born.get(Calendar.MONTH);
            int bornDay = born.get(Calendar.DAY_OF_MONTH);
            if ((currMonth < bornMonth) || (currMonth == bornMonth && currDay < bornDay)) {
                age--;
            }
            return age < 0 ? 0 : age;
        } catch (Exception e) {
            return -2;
        }
    }

    //比较日期 前>后：false
    public static boolean checkDate(String strDate,String strDate1){
        if(strDate.compareTo(strDate1)>0){
            return false;
        }
        return true;
    }

    //比较日期 前>=后：false
    public static boolean checkEdorEffectiveDate(String strDate,String strDate1){
        if(strDate.compareTo(strDate1)>=0){
            return false;
        }
        return true;
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        long ts = 0;
        try {
            date = simpleDateFormat.parse(s);
            ts = date.getTime();
        } catch (ParseException e) {
            log.info("", e);
        }
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 时间戳转换为日期格式字符串
     *
     */
    public static String timeStamp2Date(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.valueOf(timeStamp + "000")));
    }


    //获取指定时间
    public static String getAppointDate(Date date,int value,String dateUnit) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        switch (dateUnit) {
            case "Y":
                calendar.add(Calendar.YEAR, value);//把日期往后增加一年.整数往后推,负数往前移动
                break;
            case "M":
                calendar.add(Calendar.MONTH,value);//把日期往后增加一月.整数往后推,负数往前移动
                break;
            case "D":
                calendar.add(Calendar.DAY_OF_MONTH,value);//把日期往后增加一天.整数往后推,负数往前移动
                break;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String newStr = df.format(calendar.getTime());
        return newStr;
    }

    //日期格式转换得到yyyy-mm-dd的字符串格式的值
    public static String dateFormat(String dateString){
        if(StringUtils.isEmpty(dateString)){
            return null;
        }
        Date date = null;

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if(dateString.contains("-")){
                date = df.parse(dateString);
                return df.format(date);
            }else if(dateString.contains("/")){
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                date = df1.parse(dateString);
                return df.format(date);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期字符串转日期类型
     *
     * @param dateStr   日期字符串
     * @param formatStr 格式化样式 例：yyyy-MM-dd
     * @return date
     */
    public static Date strToDate(String dateStr, String formatStr) {
        if (formatStr == null || "".equals(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            log.info("", e);
        }
        return date;
    }

    /**
     * 日期字符串加几天
     *
     * @param dateStr
     * @return
     */
    public static String dateAdd(String dateStr,int amount) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = strToDate(dateStr, "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, amount);
        Date nextYear = c.getTime();
        String dateAdd = df.format(nextYear);
        return dateAdd;
    }

    /**
     * 时间戳字符串 加几天
     *
     * @param dateStr
     * @return
     */
    public static String dateTimeAdd(String dateStr,int amount) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, amount);
        Date nextYear = c.getTime();
        String dateAdd = df.format(nextYear);
        return dateAdd;
    }

    /**
     * 日期转换
     * @param dateString
     * @return
     */
    public static String formatDateString(String dateString){
        DateFormat formatStyle = new SimpleDateFormat("yyyy年MM月dd日");
        Date strToDate = DateTimeUtil.strToDate(dateString, "yyyy-MM-dd HH:mm:ss");
        String format = formatStyle.format(strToDate);
        return format;
    }

}
