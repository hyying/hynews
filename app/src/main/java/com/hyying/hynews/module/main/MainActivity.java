package com.hyying.hynews.module.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.hyying.hynews.R;
import com.hyying.hynews.base.BaseActivity;
import com.hyying.hynews.module.main.ui.MainTabPagerAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_navigationview)
    public NavigationView mNavigationView;
    @BindView(R.id.main_tablayout)
    public TabLayout mTabLayout;
    @BindView(R.id.main_viewpager)
    public ViewPager mViewPager;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        MainTabPagerAdapter mainTabPagerAdapter = new MainTabPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mainTabPagerAdapter);
        mTabLayout.setTabsFromPagerAdapter(mainTabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
