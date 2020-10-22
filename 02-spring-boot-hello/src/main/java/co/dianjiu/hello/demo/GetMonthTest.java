package co.dianjiu.hello.demo;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetMonthTest {
    /**
     * 日期格式转换
     * @param formatDate 输出格式
     * @return
     */
    public static Date parseDate(String formatDate){
        if(StringUtils.isBlank(formatDate)){
            return null;
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
        DateFormat format3 = new SimpleDateFormat("yyyyMMdd");
        DateFormat format4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat format5 = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = null;
        try {
            if(formatDate.indexOf("　")>-1){
                formatDate = formatDate.replaceAll("　", "");
            }
            if((formatDate).matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}")){
                date = format.parse(formatDate);
            }else if((formatDate).matches("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}")){
                date = format2.parse(formatDate);
            }else if((formatDate).matches("[0-9]{4}[0-9]{2}[0-9]{2}")){
                date = format3.parse(formatDate);
            }else if((formatDate).matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")){
                date = format4.parse(formatDate);
            }else if((formatDate).matches("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}")){
                date = format5.parse(formatDate);
            }else {
                date = format1.parse(formatDate);
            }
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取两个日期之间的月份差
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static BigDecimal getUpIntervalMonths(String beginDateStr, String endDateStr){
        BigDecimal num1 = new BigDecimal(12);
        Date beginDate = parseDate(beginDateStr);
        Date endDate = parseDate(endDateStr);
        Calendar c = Calendar.getInstance();
        //获取保险月数(不够一个月的视为一个月)
        c.setTime(beginDate);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DATE);
        c.setTime(endDate);
        int year2 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);
        int day2 = c.get(Calendar.DATE);
        int monthDiff;
        //同年
        if (year1 == year2) {
            monthDiff = month2 - month1;
            if (day2 > day1) {
                monthDiff = monthDiff + 1;
            }
        } else {//不同年
            monthDiff = 12 * (year2 - year1) + month2 - month1;
            if (day2 > day1) {
                monthDiff = monthDiff + 1;
            }
        }
        return new BigDecimal(monthDiff);
    }

    public static void main(String[] args) {
        BigDecimal upIntervalMonths = getUpIntervalMonths("20201023000000", "20220225235959");
        System.out.println(upIntervalMonths);
    }
}
