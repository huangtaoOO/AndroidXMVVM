package com.example.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.List;

/**
 * @author: tao
 * @time: 2019/6/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class NetworkUtils {

    /**是否连接网络 */
    public static boolean hasNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = null;
        if (connectivityManager != null) {
            activeNetInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetInfo != null && activeNetInfo.isConnected();
    }

    /**获取网络连接类型 */
    public static Integer getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if (null != activeNetInfo && activeNetInfo.isAvailable() && activeNetInfo.isConnected()) {
                return activeNetInfo.getType();
            }
        }
        return null;
    }

    /**
     * 获取当前连接的Wi-Fi信息
     */
    public static WifiInfo getCurrentConnectWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    /**获取已连接的Wifi路由器的Mac地址 */
    public static String getConnectedWifiMacAddress(Context context) {
        String connectedWifiMacAddress = null;
        if (CommonUtils.isOpenWifi(context)) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> wifiList;
            if (wifiManager != null) {
                wifiList = wifiManager.getScanResults();
                WifiInfo info = wifiManager.getConnectionInfo();
                if (wifiList != null) {
                    for (int i = 0; i < wifiList.size(); i++) {
                        ScanResult result = wifiList.get(i);
                        if (TextUtils.equals(info.getBSSID(), result.BSSID)) {
                            connectedWifiMacAddress = result.BSSID;
                            break;
                        }
                    }
                }
            }
        }
        return connectedWifiMacAddress;
    }

}
