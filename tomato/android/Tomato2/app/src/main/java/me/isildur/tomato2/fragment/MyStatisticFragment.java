package me.isildur.tomato2.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import me.isildur.tomato2.R;
import me.isildur.tomato2.StatisticWebviewActivity;
import me.isildur.tomato2.data_structure.ActivityRankEntry;
import me.isildur.tomato2.data_structure.TimePair;
import me.isildur.tomato2.interfaces.FragmentLifecycle;
import me.isildur.tomato2.me.isildur.tomato2.data.TomatoHistory;
import me.isildur.tomato2.me.isildur.tomato2.data.TomatoRecord;
import me.isildur.tomato2.ui.StatisticListAdapter;
import me.isildur.tomato2.ui_controller.PieChartController;

/**
 * Created by isi on 16/1/27.
 */
public class MyStatisticFragment extends Fragment implements FragmentLifecycle {
    private View mRootView;
    private PieChartController mPieController;
    private TomatoHistory mTomatoHistory;
    private StatisticListAdapter mStatisticAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.page_day_statistic, container, false);
        mPieController = new PieChartController(getActivity(),(ViewGroup) mRootView.findViewById(R.id.pie_chart_layout));
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.activity_rank_rv);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lm);
        new ReadRecordsAndDrawPie().execute();
        /**/
        View smallBtn = mRootView.findViewById(R.id.small_btn);
        View bigBtn = mRootView.findViewById(R.id.big_btn);
        smallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("isi", "click small btn");
                /* show webview */
                Intent intent = new Intent(getActivity(), StatisticWebviewActivity.class);
                intent.setAction("ACTION_SEND");
                startActivity(intent);
            }
        });
        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return mRootView;
    }

    @Override
    public void onPauseFragment() {
    }

    @Override
    public void onResumeFragment() {
        new ReadRecordsAndDrawPie().execute();
    }

    private class ReadRecordsAndDrawPie extends AsyncTask<Void, Integer, List<TomatoRecord>> {
        @Override
        protected List<TomatoRecord> doInBackground(Void ... voids) {
            mTomatoHistory = TomatoHistory.getInstance(getActivity());
            return mTomatoHistory.getTodayRecords();
        }
        @Override
        protected void onPostExecute(List<TomatoRecord> records) {
            boolean isNoRecord = false;
            if(null == records) isNoRecord = true;
            else if(records.isEmpty()) isNoRecord = true;
            if(isNoRecord)
                showNoRecordHint(true);
            else
                showNoRecordHint(false);
            /* draw pie */
            sortActivitiesAndDraw(records);
        }
    }

    private void showNoRecordHint(boolean isShow) {
        View hint = mRootView.findViewById(R.id.today_no_record_hint);
        if (isShow)
            hint.setAlpha(1);
        else
            hint.setAlpha(0);
    }

    private void sortActivitiesAndDraw(List<TomatoRecord> records) {
        /* sort records */
        Map<String, List<TimePair>> map = new HashMap<>();
        int tomatoSum = 0;
        if(null == records)
            return;
        for (TomatoRecord record : records) {
            String activity = record.getmActivity();
            TimePair timePair = new TimePair(record.getmStartTime(),record.getmEndTime());
            if (map.containsKey(activity)) {
                map.get(activity).add(timePair);
            } else {
                List<TimePair> list = new ArrayList<>();
                list.add(timePair);
                map.put(activity, list);
            }
            tomatoSum++;
        }
        PriorityQueue<ActivityRankEntry> rank = new PriorityQueue<>();
        for (Map.Entry<String, List<TimePair>> entry : map.entrySet()) {
            rank.add(new ActivityRankEntry(entry.getKey(), entry.getValue()));
        }

        /* setup pie chart */
        List<ActivityRankEntry> rankEntryList = new ArrayList<>();
        mPieController.reset();
        mPieController.setTomatoSum(tomatoSum);
        while(!rank.isEmpty()) {
            mPieController.addPieSector(rank.peek());
            rankEntryList.add(rank.poll());
        }
        /* setup statistic list */
        mStatisticAdapter = new StatisticListAdapter(rankEntryList, mPieController);
        mRecyclerView.setAdapter(mStatisticAdapter);
    }
}
