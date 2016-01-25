package me.isildur.tomato2;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import me.isildur.tomato2.me.isildur.tomato2.data.TomatoHistory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private TomatoTimer mTomatoTimer;
    private FloatingActionButton mFab;
    private TomatoHistory mTomatoHistory;
    private long mCountdownMs = 1*1000*60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initList();
        initActions();
    }
    private void initView() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mTomatoTimer = (TomatoTimer) findViewById(R.id.timer);
        mTomatoTimer.setMillisAll(mCountdownMs);
        mTomatoTimer.setMillisLeft(mCountdownMs);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }
    private void initList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // set adapter via db data
        new WriteDbTask().execute();
        new ReadDbTask().execute();
    }
    private void initActions() {
        /* fab listener */
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                /* ask user for tomato content */
                
                /* start/stop timer, count down for 25 minutes */
                new CountDownTimer(mCountdownMs, 1000) {
                    public void onTick(long millisUntilFinished) {
                        mTomatoTimer.setMillisLeft(millisUntilFinished);
                    }
                    public void onFinish() {
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Time is up")
                                .setMessage("Take a rest, grab a beer~")
                                .show();
                        mTomatoTimer.setMillisAll(mCountdownMs);
                        mTomatoTimer.setMillisLeft(mCountdownMs);
                        view.setEnabled(true);
                    }
                }.start();
                view.setEnabled(false);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_favorite:
                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    private class ReadDbTask extends AsyncTask<Void, Integer, List<TomatoRecord>> {
        @Override
        protected List<TomatoRecord> doInBackground(Void ... voids) {
            mTomatoHistory = TomatoHistory.getInstance(getApplicationContext());
            return mTomatoHistory.getAllRecords();
        }

        @Override
        protected void onPostExecute(List<TomatoRecord> records) {
            mAdapter = new RecordListAdapter(records);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class WriteDbTask extends  AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Calendar now = new GregorianCalendar();
            Calendar time1 = (Calendar) now.clone(); time1.add(Calendar.HOUR, 1);
            Calendar time2 = (Calendar) time1.clone(); time2.add(Calendar.HOUR, 1);
            TomatoRecord record0 = new TomatoRecord(now, 25, "body.lifting");
            TomatoRecord record1 = new TomatoRecord(time1, 25, "family.chat with parent");
            TomatoRecord record2 = new TomatoRecord(time2, 25, "work.learn android");
            mTomatoHistory = TomatoHistory.getInstance(getApplicationContext());
            mTomatoHistory.addRecord(record0);
            mTomatoHistory.addRecord(record1);
            mTomatoHistory.addRecord(record2);
            return null;
        }

    }
}
