package me.isildur.tomato2;

import java.util.Calendar;


/**
 * Created by isi on 16/1/24.
 */
public class TomatoRecord {
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mActivity;

    public TomatoRecord(Calendar stime, Calendar etime, String whatdo) {
        mStartTime = stime;
        mEndTime = etime;
        mActivity = whatdo;
    }

    public TomatoRecord(Calendar stime, int lastTimeInMin, String whatdo) {
        mStartTime = stime;
        mEndTime = (Calendar) stime.clone();
        mEndTime.add(Calendar.MINUTE, lastTimeInMin);
        mActivity = whatdo;
    }

    public Calendar getmStartTime() {
        return mStartTime;
    }
    public void setmStartTime(Calendar mStartTime) {
        this.mStartTime = mStartTime;
    }

    public Calendar getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(Calendar mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getmActivity() {
        return mActivity;
    }
    public void setmActivity(String mActivity) {
        this.mActivity = mActivity;
    }


}
