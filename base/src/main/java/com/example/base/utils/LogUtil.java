package com.example.base.utils;

import android.util.Log;
import com.example.network.BuildConfig;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 日志工具类
 */
public class LogUtil {

    public static void i(Object object , String message){
        i(object.getClass().getCanonicalName(),message);
    }

    public static void e(Object object , String message){
        e(object.getClass().getCanonicalName(),message);
    }

    public static void i(String tag , String message){
        if (BuildConfig.DEBUG){
            Log.i(tag,message);
        }
    }

    public static void e(String tag , String message){
        if (BuildConfig.DEBUG){
            Log.e(tag,message);
        }
    }
}
