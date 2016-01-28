package me.isildur.tomato2.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.isildur.tomato2.fragment.MyDayFragment;
import me.isildur.tomato2.fragment.MyStatisticFragment;
import me.isildur.tomato2.fragment.PeopleFragment;

/**
 * Created by isi on 16/1/27.
 */
public class PagerCollectionAdapter extends FragmentPagerAdapter {
    private Fragment mMyDayFragment;
    private Fragment mMyStatisticFragment;
    private Fragment mPeopleFragment;

    public PagerCollectionAdapter(FragmentManager fm) {
        super(fm);
        mMyDayFragment = new MyDayFragment();
        mMyStatisticFragment = new MyStatisticFragment();
        mPeopleFragment = new PeopleFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "title " + position;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mMyDayFragment;
            case 1:
                return mMyStatisticFragment;
            case 2:
                return mPeopleFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
