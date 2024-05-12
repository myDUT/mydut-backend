package com.capstoneproject.mydut.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;

/**
 * @author vndat00
 * @since 5/11/2024
 */
public class DateTimeUtils {
//    private static final String pattern = "yyyy-MM-dd HH:mm";
//    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//    public static Timestamp string2Timestamp(String datetimeTxt) throws ParseException {
//        return new Timestamp(simpleDateFormat.parse(datetimeTxt).getTime());
//    }
//
//    public static String timestamp2String(Timestamp timestamp) {
//        return simpleDateFormat.format(timestamp);
//    }

    public static Timestamp string2Timestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    public static String timestamp2String(Timestamp timestamp) {
        return timestamp.toString();
    }

    public static Date resetSecondOfDateTime(Date input) {
        Date res = null;
        if (input != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            res = cal.getTime();
        }
        return res;
    }

    public static Date move2EndTimeOfDay(Date input) {
        Date res = null;
        if (input != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 99);
            res = cal.getTime();
        }
        return res;
    }

    public static Date move2BeginTimeOfDay(Date input) {
        Date res = null;
        if (input != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            res = cal.getTime();
        }
        return res;
    }

    public static Date generateDateTimeFromDateAndTime(Date date, Date time) {
        Calendar res = Calendar.getInstance();
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            res.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            res.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            res.set(Calendar.DATE, cal.get(Calendar.DATE));
        }
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);

            res.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
            res.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            res.set(Calendar.SECOND, 0);
            res.set(Calendar.MILLISECOND, 0);
        }
        return res.getTime();
    }

    public static void main(String[] args) throws ParseException {
        String x = "1716193087000";
        String y = "1715501887000";
        System.out.println(string2Timestamp(x));
        System.out.println(resetSecondOfDateTime(string2Timestamp(x)));

        System.out.println(new Timestamp(generateDateTimeFromDateAndTime(string2Timestamp(x), string2Timestamp(y)).getTime()));
    }
}
