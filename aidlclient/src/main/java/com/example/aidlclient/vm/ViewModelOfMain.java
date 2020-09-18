package com.example.aidlclient.vm;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.common.viewmodel.RxJavaViewModel;

/**
 * @author: tao
 * @time: 2020/9/18
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewModelOfMain extends RxJavaViewModel {

    public final MutableLiveData<Boolean> isBind = new MutableLiveData<>(false);

    public ViewModelOfMain(Application application) {
        super(application);
    }
}
