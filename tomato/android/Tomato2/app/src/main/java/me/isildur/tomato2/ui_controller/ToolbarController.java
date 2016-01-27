package me.isildur.tomato2.ui_controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import me.isildur.tomato2.R;

/**
 * Created by isi on 16/1/27.
 */
public class ToolbarController {
    private List<ImageButton> mButtons;
    private ActionBar mActionBar;

    public ToolbarController(AppCompatActivity context, View staticView, int resId) {
        Toolbar myToolbar = (Toolbar) staticView;
        context.setSupportActionBar(myToolbar);
        mActionBar = context.getSupportActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(context.getLayoutInflater().inflate(resId, null));
        ImageButton btn0 = (ImageButton) mActionBar.getCustomView().findViewById(R.id.appbar_btn01);
        ImageButton btn1 = (ImageButton) mActionBar.getCustomView().findViewById(R.id.appbar_btn02);
        ImageButton btn2 = (ImageButton) mActionBar.getCustomView().findViewById(R.id.appbar_btn03);
        mButtons = new ArrayList<ImageButton>();
        mButtons.add(btn0);
        mButtons.add(btn1);
        mButtons.add(btn2);
    }

    public void setTabClickListener(int index, View.OnClickListener listener) {
        mButtons.get(index).setOnClickListener(listener);
    }

    public void setActive(int index) {
        for(int i=0; i<mButtons.size(); i++) {
            if (i == index)
                mButtons.get(i).setAlpha((float)1.0);
            else
                mButtons.get(i).setAlpha((float)0.3);
        }
    }
}
