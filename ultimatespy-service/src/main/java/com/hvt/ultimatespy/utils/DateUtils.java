package com.hvt.ultimatespy.utils;

import java.sql.Timestamp;
import java.time.LocalDate;

public class DateUtils {

    public static long diff(Timestamp time1, Timestamp time2)
    {
        long milliseconds1 = time1.getTime();
        long milliseconds2 = time2.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffHours;
    }

    public static int compareDateOnly(Timestamp time1, Timestamp time2) {
        LocalDate date1 = time1.toLocalDateTime().toLocalDate();
        LocalDate date2 = time2.toLocalDateTime().toLocalDate();
        return date1.compareTo(date2);
    }

}
