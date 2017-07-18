package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import com.kayac.lobi.sdk.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static final String getTimeSpan(Context context, long time) {
        long diff = (System.currentTimeMillis() - time) / 1000;
        if (diff < 60) {
            return context.getString(R.string.lobi__int__seconds, new Object[]{Long.valueOf(diff)});
        }
        diff /= 60;
        if (diff < 60) {
            return context.getString(R.string.lobi__int__minutes, new Object[]{Long.valueOf(diff)});
        }
        diff /= 60;
        if (diff < 24) {
            return context.getString(R.string.lobi__int__hours, new Object[]{Long.valueOf(diff)});
        }
        if (diff / 24 >= 7) {
            return DateFormat.getDateInstance(3).format(new Date(time));
        }
        return context.getString(R.string.lobi__int__days, new Object[]{Long.valueOf(diff)});
    }

    public static final String getLongTime(long time) {
        String yearMonthDay = DateFormat.getDateInstance(3).format(new Date(time));
        Calendar.getInstance().setTimeInMillis(time);
        return yearMonthDay + " " + String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13))});
    }

    public static final String getLongTimeSpan(Context context, long time) {
        String yearMonthDay = new SimpleDateFormat(((SimpleDateFormat) DateFormat.getDateInstance(3)).toPattern().replaceAll("\\W?[Yy]+\\W?", ""), Locale.getDefault()).format(new Date(time));
        Calendar.getInstance().setTimeInMillis(time);
        return yearMonthDay + " " + String.format("%02d:%02d", new Object[]{Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12))});
    }

    public static DateFormat getShortDateInstanceWithoutYears(Locale locale) {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance(3, locale);
        sdf.applyPattern(sdf.toPattern().replaceAll("[^\\p{Alpha}]*y+[^\\p{Alpha}]*", ""));
        return sdf;
    }

    public static final String getShortTime(long time) {
        return DateFormat.getTimeInstance(3).format(new Date(time));
    }

    public static boolean isElapsedTime(Date fromDate, long elapsed) {
        if (fromDate != null && elapsed >= 0 && new Date().getTime() >= fromDate.getTime() + elapsed) {
            return true;
        }
        return false;
    }
}
