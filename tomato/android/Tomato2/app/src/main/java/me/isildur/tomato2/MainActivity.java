package me.isildur.tomato2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import me.isildur.tomato2.me.isildur.tomato2.data.TomatoHistory;
import me.isildur.tomato2.me.isildur.tomato2.data.TomatoRecord;
import me.isildur.tomato2.ui.OnDialogConfirm;
import me.isildur.tomato2.ui.RecordListAdapter;
import me.isildur.tomato2.ui.TomatoContentDialog;
import me.isildur.tomato2.ui.TomatoTimerView;
import me.isildur.tomato2.ui_controller.TimerController;

public class MainActivity extends AppCompatActivity implements OnDialogConfirm {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecordListAdapter mAdapter;
    private TimerController mTimerController;
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
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mTimerController = new TimerController(this, (TomatoTimerView) findViewById(R.id.timer), mFab);
        mTimerController.setDefaultTime(mCountdownMs);
    }
    private void initList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //new WriteDbTask().execute();
        new ReadRecordsAndBindTask().execute();
    }
    private void initActions() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new TomatoContentDialog().show(getFragmentManager(), "content_dialog");
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_favorite:
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

    private class ReadRecordsAndBindTask extends AsyncTask<Void, Integer, List<TomatoRecord>> {
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

    private class AddTomatoTask extends  AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            if(strings.length < 1)
                return null;
            Log.i("isi","add task str: " + strings[0]);
            Calendar now = new GregorianCalendar();
            TomatoRecord record = new TomatoRecord(now, 25, strings[0]);
            mTomatoHistory = TomatoHistory.getInstance(getApplicationContext());
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
