package me.isildur.tomato2.data_structure;

import java.util.Comparator;
import java.util.List;

/**
 * Created by isi on 16/1/28.
 */
public class ActivityRankEntry implements Comparable<ActivityRankEntry> {
    private String activity;
    private List<TimePair> timeList;

    public ActivityRankEntry() {activity=null; timeList=null;}
    public ActivityRankEntry(String activity, List<TimePair> timeList) {
        this.activity = activity;
        this.timeList = timeList;
    }

    public String getActivity() {
        return activity;
    }

    public List<TimePair> getTimeList() {
        return timeList;
    }

    @Override
    public int compareTo(ActivityRankEntry o) {
        int ownLen = this.timeList.size();
        int otherLen = o.timeList.size();
        /* priority queue is min heap, if we want pq.poll() to produce the max item,
           comparator needs to be reversed */
        return otherLen - ownLen;
    }
}
