package me.isildur.tomato2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.isildur.tomato2.R;
import me.isildur.tomato2.interfaces.FragmentLifecycle;

/**
 * Created by isi on 16/1/28.
 */
public class PeopleFragment extends Fragment implements FragmentLifecycle {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("isi", "people page on create view");
        mRootView = inflater.inflate(R.layout.page_people,container,false);
        return mRootView;
    }

    @Override
    public void onPauseFragment() {
    }

    @Override
    public void onResumeFragment() {
        Log.i("isi", "people page on resume fragment");
    }

}
