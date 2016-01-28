package me.isildur.tomato2.data_structure;

import java.util.Calendar;

/**
 * Created by isi on 16/1/28.
 */
public class TimePair {
    private Calendar startTime;
    private Calendar endTime;

    public TimePair(Calendar startTime, Calendar endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }
}
