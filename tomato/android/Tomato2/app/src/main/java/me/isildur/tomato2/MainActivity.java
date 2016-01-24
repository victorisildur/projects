package me.isildur.tomato2;

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
import android.widget.ImageView;
import com.melnykov.fab.FloatingActionButton;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private TomatoTimer mTomatoTimer;
    private FloatingActionButton mFab;
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
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }
    private void initList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        TomatoRecord[] myDataset = new TomatoRecord[] {new TomatoRecord(Calendar.getInstance(), "excersing"),
                new TomatoRecord(Calendar.getInstance(),"studying"),
                new TomatoRecord(Calendar.getInstance(),"taking shower")};
        mAdapter = new RecordListAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void initActions() {
        /* fab listener */
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* start/stop timer, count down for 25 minutes */
                new CountDownTimer(25*1000*60, 1000) {
                    public void onTick(long millisUntilFinished) {
                        mTomatoTimer.setMillisLeft(millisUntilFinished);
                    }
                    public void onFinish() {
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("Time is up")
                                .setMessage("Take a rest, grab a beer~")
                                .show();
                    }
                }.start();
                view.setEnabled(false);
                ((FloatingActionButton)view).setImageResource(R.mipmap.ic_play_arrow_white_24dp);
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

}
