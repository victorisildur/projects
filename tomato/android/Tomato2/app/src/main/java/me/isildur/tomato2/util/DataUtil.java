package me.isildur.tomato2.util;

import android.database.Cursor;
import android.util.Log;

import java.util.Calendar;

import me.isildur.tomato2.me.isildur.tomato2.data.TomatoRecord;
import me.isildur.tomato2.me.isildur.tomato2.data.HistoryContract;

/**
 * Created by isi on 16/1/25.
 */
public class DataUtil {
    public static TomatoRecord constructTomatoRecord(Cursor cursor) {
        long startTime = cursor.getLong(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_START_TIME));
        long endTime   = cursor.getLong(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_END_TIME));
        String  activity  = cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_ACTIVITY));
        Calendar sTime = Calendar.getInstance();
        Calendar eTime = (Calendar) sTime.clone();
        sTime.setTimeInMillis(startTime);
        eTime.setTimeInMillis(endTime);
        TomatoRecord record = new TomatoRecord(sTime,eTime,activity);
        return record;
    }
}
