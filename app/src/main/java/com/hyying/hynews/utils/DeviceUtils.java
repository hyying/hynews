package com.hyying.hynews.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    public static final String getOsInfo() {
        return Build.VERSION.RELEASE;
    }

    public static final String getPhoneModelWithManufacturer() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机系统版本
     *
     * @return
     */
    public static String getSystemName() {
        return "Android" + getOsInfo();
    }

    /**
     * 获取TelephonyManager, target 23需要确认权限才能获取
     *
     * @param context
     * @return
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        } catch (SecurityException e) {
            LogUtils.e(TAG, e.getMessage());
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * 获取手机的设备唯一标识IMEI
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";
        try {
            deviceId = SPUtils.shareInstance().readDeviceId();
            if (TextUtils.isEmpty(deviceId)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = tm.getDeviceId();
                SPUtils.shareInstance().writeDeviceId(deviceId);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /**
     * 获取本机Mac地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        String macAddress = SPUtils.shareInstance().readMacAddress();
        if (TextUtils.isEmpty(macAddress)) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            macAddress = info.getMacAddress();
            SPUtils.shareInstance().writeMacAddress(macAddress);
        }
        return macAddress;
    }

    public static String getUUID(Context context) {
        SPUtils preferencesUtils = SPUtils.shareInstance();
        String mUUID = preferencesUtils.readUUID();
        if (TextUtils.isEmpty(mUUID)) {
            mUUID = UUID.randomUUID().toString();
            preferencesUtils.writeUUID(mUUID);
        }
        LogUtils.d("uuid = " + mUUID);
        return mUUID;
    }

    public static String getNetworkOperatorName(Context context) {
        TelephonyManager manager = getTelephonyManager(context);
        return manager != null ? manager.getNetworkOperatorName() : null;
    }
}
