package me.isildur.tomato2.ui_controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import me.isildur.tomato2.ui.TomatoTimerView;

/**
 * Created by isi on 16/1/26.
 */
public class TimerController {
    private Context mContext;
    private TomatoTimerView mTimerView;
    private View mButton;
    private long mDefaultTimeInMs;

    public TimerController(Context context, TomatoTimerView view, View actionButton) {
        mContext = context;
        mTimerView = view;
        mButton = actionButton;
        mDefaultTimeInMs = 25*60*1000;
    }

    public void setDefaultTime(long defaultTimeInMs) {
        mTimerView.setMillisLeft(defaultTimeInMs);
        mTimerView.setMillisAll(defaultTimeInMs);
        mDefaultTimeInMs = defaultTimeInMs;
    }

    public void startCountDown() {
        new CountDownTimer(mDefaultTimeInMs, 1000) {
            public void onTick(long millisUntilFinished) {
                mTimerView.setMillisLeft(millisUntilFinished);
            }
            public void onFinish() {
                new AlertDialog.Builder(mTimerView.getContext())
                        .setTitle("Time is up")
                        .setMessage("Take a rest, grab a beer~")
                        .show();
                mTimerView.setMillisAll(mDefaultTimeInMs);
                mTimerView.setMillisLeft(mDefaultTimeInMs);
                mButton.setEnabled(true);
            }
        }.start();
    }
}
