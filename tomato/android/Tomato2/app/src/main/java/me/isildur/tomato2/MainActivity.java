package me.isildur.tomato2;


import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import me.isildur.tomato2.ui.PagerCollectionAdapter;
import me.isildur.tomato2.ui_controller.ToolbarController;


public class MainActivity extends AppCompatActivity {

    private PagerCollectionAdapter mPagerCollectionAdapter;
    private ViewPager mViewPager;
    private ToolbarController mToolbarController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPager();
        setToolbar();
    }

    private void setPager() {
        mPagerCollectionAdapter = new PagerCollectionAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerCollectionAdapter);
    }

    private void setToolbar() {
        mToolbarController = new ToolbarController(this, findViewById(R.id.my_toolbar), R.layout.appbar);
        for (int i=0; i<2; i++) {
            final int finalI = i;
            mToolbarController.setTabClickListener(i, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(finalI);
                }
            });
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
            @Override
            public void onPageSelected(int position) {
                mToolbarController.setActive(position);
            }
        });
        mToolbarController.setActive(0);
    }
}
