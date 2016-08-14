package com.hyying.hynews.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        initArgs();
        initUi(savedInstanceState);
        initAction();
        initData();
    }

    protected abstract void setContentView();

    protected void initArgs() {
    }

    protected void initUi(Bundle savedInstanceState){}

    protected void initAction() {
    }


    protected void initData() {
    }
}
