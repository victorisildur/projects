package me.isildur.tomato2;

import java.util.Calendar;


/**
 * Created by isi on 16/1/24.
 */
public class TomatoRecord {
    private Calendar mTime;
    private String mDoContent;

    public TomatoRecord(Calendar time, String whatdo) {
        mTime = time;
        mDoContent = whatdo;
    }

    public Calendar getmTime() {
        return mTime;
    }

    public void setmTime(Calendar mTime) {
        this.mTime = mTime;
    }

    public String getmDoContent() {
        return mDoContent;
    }

    public void setmDoContent(String mDoContent) {
        this.mDoContent = mDoContent;
    }

}
