package com.tao.androidx_mvvm.view_model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.common.viewmodel.RxJavaViewModel;

/**
 * @author: tao
 * @time: 2020/9/3
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewModelOfMain extends RxJavaViewModel {

    public final MutableLiveData<String> text = new MutableLiveData<>("hello");

    public ViewModelOfMain(Application application) {
        super(application);
    }
}
