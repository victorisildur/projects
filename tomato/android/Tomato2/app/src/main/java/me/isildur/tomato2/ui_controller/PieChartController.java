package me.isildur.tomato2.ui_controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.isildur.tomato2.data_structure.ActivityRankEntry;
import me.isildur.tomato2.data_structure.PieColor;
import me.isildur.tomato2.ui.PieSectorView;

/**
 * Created by isi on 16/1/28.
 */
public class PieChartController {
    public ViewGroup mParentView;
    public Context mContext;
    private int mStartDegree;
    private int mTomatoSum;
    private int mFirstColor;
    private int mPrevColor;
    private List<PieSectorView> mPieSectorViews;

    public PieChartController(Context context, ViewGroup parentView) {
        mContext = context;
        mParentView = parentView;
        mStartDegree = 0;
        mTomatoSum = 1;
        mFirstColor = 0;
        mPieSectorViews = new ArrayList<>();
    }

    public void hide() {
        mParentView.removeAllViews();
    }

    public void reset() {
        mStartDegree = 0;
        mFirstColor = mPrevColor = 0;
        mPieSectorViews = new ArrayList<>();
    }

    public void setTomatoSum(int sum) {
        mTomatoSum = sum;
    }

    public void addPieSector(ActivityRankEntry activity) {
        int sweepDegree = (int) Math.ceil(((double) activity.getTimeList().size() / mTomatoSum) * 360);
        int color = genColor();
        PieSectorView pieSectorView = new PieSectorView(mContext,mParentView,mStartDegree,sweepDegree,color,activity.getActivity());
        mPieSectorViews.add(pieSectorView);

        mStartDegree += sweepDegree;
        mParentView.addView(pieSectorView);
        mParentView.invalidate();
    }

    public void enlarge(int position) {
        if (position<0 || position>=mPieSectorViews.size())
            return;
        for (int i=0; i<mPieSectorViews.size(); i++) {
            if(position == i)
                mPieSectorViews.get(i).enlarge();
            else
                mPieSectorViews.get(i).normal();
        }
    }

    private int genColor() {
        int color = PieColor.COLORS[(int) (Math.random()*PieColor.COLORS.length)];
        if (color == mPrevColor || color == mFirstColor)
            return genColor();
        if (mFirstColor == 0)
            mFirstColor = color;
        mPrevColor = color;
        return color;
    }
}
