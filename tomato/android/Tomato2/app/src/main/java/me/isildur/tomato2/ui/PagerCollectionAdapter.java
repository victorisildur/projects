package me.isildur.tomato2.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.isildur.tomato2.fragment.MyDayFragment;
import me.isildur.tomato2.fragment.MyStatisticFragment;

/**
 * Created by isi on 16/1/27.
 */
public class PagerCollectionAdapter extends FragmentPagerAdapter {

    public PagerCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "title " + position;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyDayFragment();
            case 1:
                return new MyStatisticFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
