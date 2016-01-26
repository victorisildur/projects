package me.isildur.tomato2.ui_controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import me.isildur.tomato2.R;
import me.isildur.tomato2.ui.TomatoTimerView;

/**
 * Created by isi on 16/1/26.
 */
public class TimerController {
    private Context mContext;
    private TomatoTimerView mTimerView;
    private View mButton;
    private long mDefaultTimeInMs;
    private long mRestTimeInMs;
    private State mState;
    public enum State {
        RUNNING, RESTING, REST_DONE
    }

    public TimerController(Context context, TomatoTimerView view, View actionButton) {
        mContext = context;
        mTimerView = view;
        mButton = actionButton;
        mDefaultTimeInMs = mContext.getResources().getInteger(R.integer.default_time_run);
        mRestTimeInMs = mContext.getResources().getInteger(R.integer.default_time_rest);
        mState = State.RUNNING;
        setDefaultTime(getCountDownTime());
        setViewState();
    }

    public void startCountDown() {
        if (mState == State.REST_DONE)
            altState();
        setButtonEnable();
        new CountDownTimer(getCountDownTime(), 1000) {
            public void onTick(long millisUntilFinished) {
                mTimerView.setMillisLeft(millisUntilFinished);
            }
            public void onFinish() {
                alertTimeUp();
                altState();
                setDefaultTime(getCountDownTime());
                if(mState == State.RESTING)
                    startCountDown();
            }
        }.start();
    }

    private void setDefaultTime(long defaultTimeInMs) {
        mTimerView.setMillisLeft(defaultTimeInMs);
        mTimerView.setMillisAll(defaultTimeInMs);
    }

    private void altState() {
        switch (mState) {
            case RUNNING:
                mState = State.RESTING; break;
            case RESTING:
                mState = State.REST_DONE; break;
            case REST_DONE:
                mState = State.RUNNING;
        }
        setViewState();
    }

    private long getCountDownTime() {
        long time = 0;
        switch (mState) {
            case RUNNING:
                time = mDefaultTimeInMs; break;
            case RESTING:
                time = mRestTimeInMs; break;
        }
        return time;
    }

    private void alertTimeUp() {
        switch (mState) {
            case RUNNING:

                break;
            case RESTING:
                
        }

    }

    private void setButtonEnable() {
        /* called when start counting down */
        switch (mState) {
            case RUNNING:
                mButton.setEnabled(false); break;
            case RESTING:
                mButton.setEnabled(true);
        }
    }

    private void setViewState() {
        mTimerView.setState(mState);
    }
}
