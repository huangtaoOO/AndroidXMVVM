package com.example.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * @author: tao
 * @time: 2020/8/21
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class BaseApplication extends Application implements ViewModelStoreOwner {

    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppViewModelStore = new ViewModelStore();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}