package me.isildur.tomato2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by isi on 16/1/23.
 */
public class TomatoTimer extends View {
    private Integer mState;
    private Paint mBgPaint;
    private Paint mImgPaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private long millisAll;
    private long millisLeft;

    public TomatoTimer(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.TomatoTimer,0,0);
        try {
            mState = a.getInteger(R.styleable.TomatoTimer_state, 0);
        } finally {
            a.recycle();
        }
        initPaint();
        millisAll = 25*1000*60;
        millisLeft = millisAll;
    }

    private void initPaint() {
        mImgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int imgColor = getResources().getColor(R.color.colorPrimaryLight);
        mImgPaint.setColor(imgColor);
        int arcColor = Color.argb(0xff,0xff,0xff,0xff);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(arcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(40);
        mArcPaint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
        mBgPaint = mImgPaint;
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
        canvas.drawRect(0,0,w,h,mBgPaint);
        /* draw circle */
        //canvas.drawCircle(w/2, h/2, d/2,mImgPaint);
        /* draw arc */
        int degree = (int) (((double)millisLeft/(double)millisAll)*360);
        Log.i("isi", "on draw, ms left:" + millisLeft + ", degree:" + degree);
        canvas.drawArc(w/2-d/2+margin,h/2-d/2+margin,
                w/2+d/2-margin,h/2+d/2-margin,
                -90,(int) degree,false,mArcPaint);
        /* draw remaining time */
        int minute = (int) ((float)millisLeft/1000/60);
        int second = (int) (millisLeft%(1000*60)/1000);
        String minStr = String.valueOf(minute);
        String secStr = String.valueOf(second);
        if(minute<10)
            minStr = "0" + minStr;
        if(second<10)
            secStr = "0" + secStr;
        String timeStr = minStr + ":" + secStr;
        canvas.drawText(timeStr, w/2, h/2+getResources().getInteger(R.integer.timer_textsize)/2, mTextPaint);
    }

    public Integer getmState() {
        return mState;
    }

    public void setmState(Integer state) {
        mState = state;
        invalidate();
        requestLayout();
    }

    public long getMillisAll() {
        return millisAll;
    }

    public void setMillisAll(long millisAll) {
        this.millisAll = millisAll;
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
