package com.example.common.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.example.common.BaseApplication;

import java.util.List;
import java.util.UUID;

/**
 * @author: tao
 * @time: 2019/6/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class CommonUtils {

    /**
     * Android是否4.4.4以上，API 19
     *
     * @return true：是
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Android是否5.0以上，API 21
     *
     * @return true：是
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Android是否6.0以上，API 23
     *
     * @return true：是
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Android是否7.0以上，API 24
     *
     * @return true：是
     */
    public static boolean hasN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Android是否8.0以上，API 26
     *
     * @return true：是
     */
    public static boolean hasOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 是否已打开Gps
     */
    public static boolean isOpenGps(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return null != locationManager && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 是否有SD卡
     */
    public static boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 是否已打开Wi-Fi
     */
    public static boolean isOpenWifi(Context context) {
        Integer networkType = NetworkUtils.getNetworkType(context);
        return null != networkType && networkType == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取androidId
     *
     * @param context context
     * @return androidId
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取imei
     *
     * @param context context
     * @return imei
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                imei = telephonyManager.getDeviceId();
            }
        }
        return imei;
    }

    /**
     * 获取设备厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备型号
     */
    public static String getDeviceMobile() {
        return Build.MODEL;
    }

    /**
     * 获取设备系统版本
     */
    public static String getDeviceOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机类型(android)
     *
     * @return android类型
     */
    public static int getMobileType() {
        return 1;
    }

    /**
     * 获取App当前版本号
     *
     * @return 版本号
     */
    public static int getAppVersion() {
        int localVersion = 0;
        Context context = BaseApplication.getInstance();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取App当前版本名称
     *
     * @return 版本名称
     */
    public static String getAppVersionName() {
        String localVersion = null;
        Context context = BaseApplication.getInstance();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 随机生成UUID
     */
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 判断APP是否已经安装
     *
     * @param context     context
     * @param packageName 包名
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        //获取已安装的APP信息
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        if (packageInfoList != null && packageInfoList.size() > 0) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                if (packageName.equals(packageInfoList.get(i).packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除应用
     */
    public static void unInstall(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:com.pmg.pmgenius.app"));
        context.startActivity(intent);
    }
}
