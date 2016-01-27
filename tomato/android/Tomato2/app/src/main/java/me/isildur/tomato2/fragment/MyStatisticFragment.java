package me.isildur.tomato2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.isildur.tomato2.R;

/**
 * Created by isi on 16/1/27.
 */
public class MyStatisticFragment extends Fragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.page_day_statistic, container, false);
        return mRootView;
    }
}
