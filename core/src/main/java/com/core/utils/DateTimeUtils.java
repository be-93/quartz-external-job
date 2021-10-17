package com.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateTimeUtils() { }

    public static String getDateTime(Date date) {
        return getDateTime(DEFAULT_DATETIME_FORMAT, date);
    }

    public static String getDateTime(String pattern, Date date) {
        if (date != null) {
            return new SimpleDateFormat(pattern).format(date);
        }
        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).format(new Date());
    }
}
