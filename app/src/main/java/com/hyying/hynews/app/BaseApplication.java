package com.hyying.hynews.app;

import android.app.Application;

public class BaseApplication extends Application {
    private AppProfile appProfile;

    @Override
    public void onCreate() {
        super.onCreate();
        appProfile = AppProfile.getAppProfile(getApplicationContext());
    }
}
