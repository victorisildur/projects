package me.isildur.tomato2.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import me.isildur.tomato2.R;
import me.isildur.tomato2.interfaces.FragmentLifecycle;
import me.isildur.tomato2.me.isildur.tomato2.data.TomatoHistory;
import me.isildur.tomato2.me.isildur.tomato2.data.TomatoRecord;
import me.isildur.tomato2.interfaces.OnDialogConfirm;
import me.isildur.tomato2.ui.RecordListAdapter;
import me.isildur.tomato2.ui.TomatoContentDialog;
import me.isildur.tomato2.ui.TomatoTimerView;
import me.isildur.tomato2.ui_controller.TimerController;

/**
 * Created by isi on 16/1/27.
 */
public class MyDayFragment extends Fragment implements OnDialogConfirm, FragmentLifecycle{
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecordListAdapter mAdapter;
    private TimerController mTimerController;
    private FloatingActionButton mFab;
    private TomatoHistory mTomatoHistory;
    private View mRootView;
    private  TomatoContentDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.page_my_day, container, false);
        initView();
        initActions();
        initList();
        return mRootView;
    }

    private void initView() {
        mFab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        mTimerController = new TimerController(getActivity(), (TomatoTimerView) mRootView.findViewById(R.id.timer), mFab);
        mDialog = new TomatoContentDialog();
        mDialog.setListener(this);

    }
    private void initList() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //new WriteDbTask().execute();
        new ReadRecordsAndBindTask().execute();
    }

    private void initActions() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mDialog.show(getActivity().getFragmentManager(), "content_dialog");
            }
        });
    }

    @Override
    public void onPauseFragment() {}
    @Override
    public void onResumeFragment() {}

    private class ReadRecordsAndBindTask extends AsyncTask<Void, Integer, List<TomatoRecord>> {
        @Override
        protected List<TomatoRecord> doInBackground(Void ... voids) {
            mTomatoHistory = TomatoHistory.getInstance(getActivity());
            return mTomatoHistory.getAllRecords();
        }
        @Override
        protected void onPostExecute(List<TomatoRecord> records) {
            mAdapter = new RecordListAdapter(records);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class AddTomatoTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            if(strings.length < 1)
                return null;
            Calendar now = new GregorianCalendar();
            int runMinute = getResources().getInteger(R.integer.default_time_run)/1000/60;
            TomatoRecord record = new TomatoRecord(now, runMinute, strings[0]);
            mTomatoHistory = TomatoHistory.getInstance(getActivity());
            mTomatoHistory.addRecord(record);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.setmDataSet(mTomatoHistory.getAllRecords());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogConfirm(boolean ifConfirm, String content) {
        if(!ifConfirm)
            return;
        mTimerController.startCountDown();
        new AddTomatoTask().execute(content);
    }

}
