package me.isildur.tomato2.me.isildur.tomato2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by isi on 16/1/25.
 */
public class HistoryDbHelper extends SQLiteOpenHelper {
    private static final String TIME_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE =
            "Create Table " + HistoryContract.HistoryEntry.TABLE_NAME + " (" +
                    HistoryContract.HistoryEntry._ID + " INTEGER PRIMARY KEY," +
                    HistoryContract.HistoryEntry.COLUMN_NAME_START_TIME + TIME_TYPE + COMMA_SEP +
                    HistoryContract.HistoryEntry.COLUMN_NAME_END_TIME   + TIME_TYPE + COMMA_SEP +
                    HistoryContract.HistoryEntry.COLUMN_NAME_ACTIVITY   + TEXT_TYPE + " )";
    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + HistoryContract.HistoryEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "history.db";

    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
