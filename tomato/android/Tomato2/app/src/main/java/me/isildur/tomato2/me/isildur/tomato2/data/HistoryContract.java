package me.isildur.tomato2.me.isildur.tomato2.data;

import android.provider.BaseColumns;

/**
 * Created by isi on 16/1/25.
 */
public final class HistoryContract {
    public HistoryContract() {}
    public static abstract class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "MyHistory";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_NAME_ACTIVITY = "activity";
    }

}
