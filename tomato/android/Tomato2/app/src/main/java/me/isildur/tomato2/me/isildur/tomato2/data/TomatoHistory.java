package me.isildur.tomato2.me.isildur.tomato2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import me.isildur.tomato2.TomatoRecord;
import me.isildur.tomato2.util.DataUtil;

/**
 * Created by isi on 16/1/25.
 */
public class TomatoHistory {
    private List<TomatoRecord> mRecords;
    private HistoryDbHelper mDbHelper;

    public static TomatoHistory mInstance;

    public static TomatoHistory getInstance() throws Exception {
        if(mInstance == null)
            throw new Exception("has not init yet");
        return mInstance;
    }

    public static TomatoHistory getInstance(Context context) {
        if(mInstance == null)
            mInstance = new TomatoHistory(context);
        return mInstance;
    }

    private TomatoHistory(Context context) {
        mDbHelper = new HistoryDbHelper(context);
        getAllRecordFromDb();
    }

    public List<TomatoRecord> getAllRecords() {
        return getAllRecordFromDb();
    }

    public boolean addRecord(TomatoRecord record) {
        mRecords.add(record);
        insertRecordToDb(record);
        return true;
    }

    private List<TomatoRecord> getAllRecordFromDb() {
        mRecords = new LinkedList<TomatoRecord>();
        String sortOrder = HistoryContract.HistoryEntry.COLUMN_NAME_START_TIME + " DESC";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(HistoryContract.HistoryEntry.TABLE_NAME,null,null,null,null,null,sortOrder);
        if( !cursor.moveToFirst())
            return null;
        do {
            mRecords.add(DataUtil.constructTomatoRecord(cursor));
        } while (cursor.moveToNext());
        db.close();
        return mRecords;
    }

    private void insertRecordToDb(TomatoRecord record) {
        ContentValues cv = new ContentValues();
        cv.put(HistoryContract.HistoryEntry.COLUMN_NAME_START_TIME, record.getmStartTime().getTimeInMillis());
        cv.put(HistoryContract.HistoryEntry.COLUMN_NAME_END_TIME, record.getmEndTime().getTimeInMillis());
        cv.put(HistoryContract.HistoryEntry.COLUMN_NAME_ACTIVITY, record.getmActivity());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.insertOrThrow(HistoryContract.HistoryEntry.TABLE_NAME, null, cv);
        db.close();
    }
}
