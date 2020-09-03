package com.example.common.utils;

import android.widget.Toast;

import androidx.annotation.StringRes;

import com.example.common.BaseApplication;

import org.jetbrains.annotations.NotNull;

/**
 * @author: tao
 * @time: 2019/7/2
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ToastUtils {

    public static void showTextToast(@NotNull CharSequence text){
        //不能写成单例，部分机型不能连续显示Toast。
        //置null再setText是为了解决MIUI Toast自动添加app前缀的问题
        Toast sToast = Toast.makeText(BaseApplication.getInstance(), null, Toast.LENGTH_SHORT);
        sToast.setText(text);
        sToast.show();
    }

    public static void showTextToast(@StringRes int resId){
        //不能写成单例，部分机型不能连续显示Toast。
        //置null再setText是为了解决MIUI Toast自动添加app前缀的问题
        Toast sToast = Toast.makeText(BaseApplication.getInstance(), null, Toast.LENGTH_SHORT);
        sToast.setText(BaseApplication.getInstance().getString(resId));
        sToast.show();
    }

}
