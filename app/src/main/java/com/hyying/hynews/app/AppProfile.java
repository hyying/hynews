package com.hyying.hynews.app;

import android.content.Context;

public class AppProfile {
    private static AppProfile mAppProfile;
    public static Context sContext;

    private AppProfile() {
    }

    private AppProfile(Context context) {
        sContext = context;
    }

    public static AppProfile getAppProfile(Context context) {
        if (null == mAppProfile) {
            synchronized (AppProfile.class) {
                if (null == mAppProfile) {
                    mAppProfile = new AppProfile(context);
                }
            }
        }
        return mAppProfile;
    }


}
