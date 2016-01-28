package me.isildur.tomato2.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import me.isildur.tomato2.R;

/**
 * Created by isi on 16/1/28.
 */
public class PieSectorView extends View {
    private int mStartDegree;
    private int mDegree;
    private int mColor;
    private Paint mSectorPaint;
    private Paint mInnerPaint;
    private Paint mMaskPaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private int mDiameter;
    private String mActivity;
    private STATE mState;

    public static enum STATE {
        NORMAL, LARGE
    }

    public PieSectorView(Context context, View parentView, int startDeg, int degree, int color, String activity) {
        super(context);
        mStartDegree = startDeg;
        mDegree = degree;
        mActivity = activity;
        mWidth = parentView.getWidth();
        mHeight = parentView.getHeight();
        mColor = color;
        mState = STATE.NORMAL;
        initPaint();
    }

    private void initPaint() {
        mDiameter = Math.min(mWidth, mHeight);
        mDiameter = (int) (mDiameter * 0.9);
        mSectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSectorPaint.setColor(mColor);
        mSectorPaint.setStyle(Paint.Style.FILL);
        mSectorPaint.setStrokeWidth(0);

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int bgColor = getResources().getColor(R.color.pie_chart_bg);
        mInnerPaint.setColor(bgColor);
        mInnerPaint.setStyle(Paint.Style.FILL);

        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint.setColor(Color.WHITE);
        mMaskPaint.setAlpha(100);
        mMaskPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(34);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sectorDiameter, maskDiameter, innerDiameter;
        if(mState == STATE.NORMAL) {
            sectorDiameter = mDiameter;
            maskDiameter   = (int) (sectorDiameter * 0.618);
            innerDiameter  = (int) (sectorDiameter * 0.518);
        } else {
            sectorDiameter = (int) (mDiameter * 1.1);
            maskDiameter   = (int) (mDiameter * 0.63);
            innerDiameter  = (int) (mDiameter * 0.46);
        }
        drawArc(canvas, sectorDiameter, mDegree, mSectorPaint);
        drawArc(canvas, maskDiameter, mDegree, mMaskPaint);
        drawArc(canvas, innerDiameter, 360, mInnerPaint);
        /* draw text */
        int textDiameter = (mDiameter+maskDiameter)/2;
        RectF textRect = new RectF(mWidth/2-textDiameter/2,mHeight/2-textDiameter/2,mWidth/2+textDiameter/2,mHeight/2+textDiameter/2);
        Path textPath = new Path();
        textPath.addArc(textRect, -90 + mStartDegree, mDegree);
        canvas.drawTextOnPath(mActivity, textPath, 10, 0, mTextPaint);
    }

    private void drawArc(Canvas canvas, int diameter, int sweepDeg, Paint paint) {
        RectF rect = new RectF(mWidth/2-diameter/2,mHeight/2-diameter/2,mWidth/2+diameter/2,mHeight/2+diameter/2);
        canvas.drawArc(rect, -90 + mStartDegree, sweepDeg, true, paint);
    }


    public void enlarge() {
        if (mState == STATE.LARGE)
            return;
        mState = STATE.LARGE;
        invalidate();
        requestLayout();
    }
    public void normal() {
        if (mState == STATE.NORMAL)
            return;
        mState = STATE.NORMAL;
        invalidate();
        requestLayout();
    }
}
