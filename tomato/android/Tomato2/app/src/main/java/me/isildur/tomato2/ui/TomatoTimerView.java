package me.isildur.tomato2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import me.isildur.tomato2.R;
import me.isildur.tomato2.ui_controller.TimerController;
import me.isildur.tomato2.util.TimeUtil;

import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by isi on 16/1/23.
 */
public class TomatoTimerView extends View {
    private Paint mBgPaint;
    private Paint mArcPaint;
    private Paint mCirclePaint;
    private Paint mImgPaint;
    private Paint mTextPaint;

    private long millisAll;
    private long millisLeft;
    private String mCenterStr;

    private TimerController.State mState;

    public TomatoTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TomatoTimerView,0,0);
        initPaint();
        millisAll = context.getResources().getInteger(R.integer.default_time_run);
        millisLeft = millisAll;
        mState = TimerController.State.RUNNING;
    }

    private void initPaint() {
        /* Background */
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int imgColor = getResources().getColor(R.color.colorPrimaryLight);
        mBgPaint.setColor(imgColor);
        /* Progress Arc */
        int arcColor = Color.argb(0xff,0xff,0xff,0xff);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(arcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(40);
        mArcPaint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
        /* Outer Circle */
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int circleColor = Color.argb(0x44,0xff,0xff,0xff);
        mCirclePaint.setColor(circleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(3);
        /* Tomato Image */
        mImgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /* Text */
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(arcColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(getResources().getInteger(R.integer.timer_textsize));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int d = Math.min(w,h)*2/3;
        int margin = 20;
        /* draw rect */
        canvas.drawRect(0, 0, w, h, mBgPaint);
        /* draw circle */
        canvas.drawArc(w / 2 - d / 2 - 2 * margin, h / 2 - d / 2 - 2 * margin,
                w / 2 + d / 2 + 2 * margin, h / 2 + d / 2 + 2 * margin,
                -80, 340, false, mCirclePaint);
        /* draw tomato logo */
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.tomato_white);
        canvas.drawBitmap(bm, null, new RectF(w / 2 - margin, h / 2 - d / 2 - 3 * margin, w / 2 + margin, h / 2 - d / 2 - margin), mImgPaint);
        /* draw arc */
        int degree = (int) (((double) millisLeft / (double) millisAll) * 360);
        canvas.drawArc(w / 2 - d / 2 + margin, h / 2 - d / 2 + margin,
                w / 2 + d / 2 - margin, h / 2 + d / 2 - margin,
                -90, (int) degree, false, mArcPaint);
        /* draw remaining time */
        setCenterStr();
        canvas.drawText(mCenterStr, w / 2, h / 2 + getResources().getInteger(R.integer.timer_textsize) / 2, mTextPaint);
    }

    private void setCenterStr() {
        switch (mState) {
            case RUNNING:
            case RESTING:
                mCenterStr = TimeUtil.msToStr(millisLeft); break;
            case REST_DONE:
                mCenterStr = getResources().getString(R.string.rest_done_hint);
        }
    }

    public void setState(TimerController.State state) {
        mState = state;
        if (null == mBgPaint)
            return;
        switch (mState) {
            case RUNNING:
                mBgPaint.setColor(getResources().getColor(R.color.colorPrimaryLight)); break;
            case RESTING:
                mBgPaint.setColor(getResources().getColor(R.color.resting)); break;
            case REST_DONE:
                mBgPaint.setColor(getResources().getColor(R.color.rest_done));

                break;
        }
        invalidate();
        requestLayout();
    }

    public long getMillisAll() {
        return millisAll;
    }

    public void setMillisAll(long millisAll) {
        this.millisAll = millisAll;
        invalidate();
        requestLayout();
    }

    public long getMillisLeft() {
        return millisLeft;
    }

    public void setMillisLeft(long millisLeft) {
        this.millisLeft = millisLeft;
        invalidate();
        requestLayout();
    }
}
