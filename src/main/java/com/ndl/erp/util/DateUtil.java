package com.ndl.erp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alvaro on 3/1/14.
 */
public class DateUtil {

    private static Locale currentLocale = new Locale("es_CR");

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es_CR"));

    public static void setCurrentLocale(String locale) {
        currentLocale = new Locale(locale);
    }

    public static Calendar getCurrentCalendar() {
        return new GregorianCalendar(TimeZone.getTimeZone("America/Costa_Rica"),currentLocale);
    }

    public static long getCurrentTime() {
        return new GregorianCalendar(TimeZone.getTimeZone("America/Costa_Rica"),currentLocale).getTimeInMillis();

    }

    public static String getNameMonthYearCr() {

        String pattern = "MMMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "CRA"));
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        return date;
    }

    public static String getNameMonthString() {

        String pattern = "MMMMM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "CRA"));
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        return date;
    }

    public static String getYearString() {

        String pattern = "yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "CRA"));
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        return date;
    }


    public static String getDateFormatted() {
        return DateFormat.getDateInstance(DateFormat.LONG,currentLocale).format(new Date(new GregorianCalendar(TimeZone.getTimeZone("America/Costa_Rica"),currentLocale).getTimeInMillis()));
    }

    public static DateFormat getCurrentDateFormat() {
        return df;
    }

    public static java.sql.Date convertDateToSqlDate(java.util.Date toConvert) {
        return new java.sql.Date(toConvert.getTime());
    }
}
