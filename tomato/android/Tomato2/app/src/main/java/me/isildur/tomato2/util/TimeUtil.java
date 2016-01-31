package me.isildur.tomato2.util;

import java.util.Calendar;

/**
 * Created by isi on 16/1/25.
 */
public class TimeUtil {
    public static String msToStr(long ms) {
        int minute = (int) ((float)ms/1000/60);
        int second = (int) (ms%(1000*60)/1000);
        String minStr = String.valueOf(minute);
        String secStr = String.valueOf(second);
        if(minute<10)
            minStr = "0" + minStr;
        if(second<10)
            secStr = "0" + secStr;
        return minStr + ":" + secStr;
    }
    public static String cldrToTimestr(Calendar cldr) {

        int hour = cldr.get(Calendar.HOUR);
        int minute = cldr.get(Calendar.MINUTE);
        String hourStr = String.valueOf(hour);
        String minStr = String.valueOf(minute);

        if(hour<10)
            hourStr = "0" + hourStr;
        if(minute<10)
            minStr = "0" + minStr;
        return  hourStr + ":" + minStr;
    }
}
