package com.example.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.BaseApplication;

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class SharedPreferencesUtils {

    /** SharedPreferences缓存名称 */
    private final static String SP_NAME = "Android_MVVM";

    /** 获取缓存 */
    public static SharedPreferences getSharedPreferences() {
        return BaseApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /** 保存单个缓存 */
    public static void saveSharedPreferences(String keyName, Object value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (value instanceof Boolean) {
            editor.putBoolean(keyName, (Boolean) value);
        }
        else if (value instanceof Integer) {
            editor.putInt(keyName, (Integer) value);
        }
        else if (value instanceof Float) {
            editor.putFloat(keyName, (Float) value);
        }
        else if (value instanceof String) {
            editor.putString(keyName, (String) value);
        }
        editor.apply();
    }
}
