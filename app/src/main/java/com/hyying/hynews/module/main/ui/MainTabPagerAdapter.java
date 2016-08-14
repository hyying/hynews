package com.hyying.hynews.module.main.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hyying.hynews.R;

public class MainTabPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MainTabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new MainPageFragment();
    }

    @Override
    public int getCount() {
        return mContext.getResources().getStringArray(R.array.main_tab).length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.main_tab)[position];
    }
}
