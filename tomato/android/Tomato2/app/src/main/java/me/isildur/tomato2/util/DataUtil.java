package me.isildur.tomato2.util;

import android.database.Cursor;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.isildur.tomato2.TomatoRecord;
import me.isildur.tomato2.me.isildur.tomato2.data.HistoryContract;

/**
 * Created by isi on 16/1/25.
 */
public class DataUtil {
    public static TomatoRecord constructTomatoRecord(Cursor cursor) {
        Integer startTime = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_START_TIME));
        Integer endTime   = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_END_TIME));
        String  activity  = cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_ACTIVITY));
        Calendar sTime = new GregorianCalendar();
        Calendar eTime = new GregorianCalendar();
        sTime.setTimeInMillis(startTime);
        eTime.setTimeInMillis(endTime);
        TomatoRecord record = new TomatoRecord(sTime,eTime,activity);
        return record;
    }
}
