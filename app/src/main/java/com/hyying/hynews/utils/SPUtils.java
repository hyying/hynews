package com.hyying.hynews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hyying.hynews.app.AppProfile;


/**
 * SharedPreferences工具类
 */
public class SPUtils {
    private static SPUtils mInstance;
    private SharedPreferences mSp;

    private SPUtils() {
    }

    public static SPUtils shareInstance() {
        if (null == mInstance) {
            synchronized (SPUtils.class) {
                if (null == mInstance) {
                    mInstance = new SPUtils();
                    mInstance.mSp = AppProfile.sContext.getSharedPreferences("hy_config", Context.MODE_PRIVATE);
                }
            }
        }
        return mInstance;
    }

    public void writeSubjectCache(String subjectCache) {
        SharedPreferences.Editor editor = this.mSp.edit();
        editor.putString(SPKey.SUBJECT_CACHE, subjectCache);
        editor.commit();
    }

    public String readSubjectCache() {
        return this.mSp.getString(SPKey.SUBJECT_CACHE, "{}");
    }

    public void writeCategoryCache(String categoryJsonStr) {
        SharedPreferences.Editor editor = this.mSp.edit();
        editor.putString(SPKey.CATEGORY_CACHE, categoryJsonStr);
        editor.commit();
    }

    public String readCategoryCache() {
        return this.mSp.getString(SPKey.CATEGORY_CACHE, "{}");
    }

    public void writeUserInfo(String appUser) {
        SharedPreferences.Editor editor = this.mSp.edit();
        editor.putString(SPKey.APP_USER, appUser);
        editor.commit();
    }

    public String readUserInfo() {
        return this.mSp.getString(SPKey.APP_USER, "{}");
    }

    public String readDeviceId() {
        return this.mSp.getString(SPKey.DEVICE_ID, "");
    }

    public void writeDeviceId(String deviceId) {
        SharedPreferences.Editor editor = this.mSp.edit();
        editor.putString(SPKey.DEVICE_ID, deviceId);
        editor.commit();
    }

    public String readMacAddress() {
        return this.mSp.getString(SPKey.MAC_ADDRESS, "");
    }

    public void writeMacAddress(String macAddress) {
        SharedPreferences.Editor editor = this.mSp.edit();
        editor.putString(SPKey.MAC_ADDRESS, macAddress);
        editor.commit();
    }

    public String readUUID() {
        return this.mSp.getString(SPKey.UUID, "");
    }

    public void writeUUID(String mUUID) {
        SharedPreferences.Editor editor = this.mSp.edit();
        editor.putString(SPKey.UUID, mUUID);
        editor.commit();
    }

    public interface SPKey {
        String SUBJECT_CACHE = "SUBJECT_CACHE";
        String CATEGORY_CACHE = "CATEGORY_CACHE";
        String APP_USER = "APP_USER";
        String DEVICE_ID = "DEVICE_ID";
        String MAC_ADDRESS = "MAC_ADDRESS";
        String UUID="uuid";
    }
}
