package com.tao.androidx_mvvm.utils;

import android.util.Log;
import com.tao.androidx_mvvm.BuildConfig;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 日志工具类
 */
public class LogUtil {
    public static final String DEBUG = "debug";

    public static void i(Object object , String message){
        i(object.getClass().getCanonicalName(),message);
    }

    public static void e(Object object , String message){
        e(object.getClass().getCanonicalName(),message);
    }

    public static void i(String tag , String message){
        if (DEBUG.equals(BuildConfig.BUILD_TYPE)){
            Log.i(tag,message);
        }
    }

    public static void e(String tag , String message){
        if (DEBUG.equals(BuildConfig.BUILD_TYPE)){
            Log.e(tag,message);
        }
    }
}
