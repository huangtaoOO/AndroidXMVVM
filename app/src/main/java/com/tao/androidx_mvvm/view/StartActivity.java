package com.tao.androidx_mvvm.view;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.common.view.BaseLoadingActivity;
import com.tao.androidx_mvvm.R;
import com.tao.androidx_mvvm.databinding.ActivityStartBinding;
import com.tao.androidx_mvvm.view_model.ViewModelOfStart;

/**
 * @author: tao
 * @time: 2020/9/22
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class StartActivity extends BaseLoadingActivity<ActivityStartBinding, ViewModelOfStart> {
    private static final String TAG = "StartActivity";

    @Override
    protected Class<ViewModelOfStart> initViewModel() {
        return ViewModelOfStart.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected int vmVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initUI() {
//        MutableLiveData<String> mutableLiveData  = new MutableLiveData<>();
//        mutableLiveData.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable final String s) {
//                Log.d(TAG, "onChanged1:"+s);
//            }
//        });
//        LiveData<String> transformedLiveData = Transformations.map(mutableLiveData, (Function<String, String>) name -> name + "+Android进阶解密");
//        transformedLiveData.observe(this, (Observer<String>) o -> Log.d(TAG, "onChanged2:"+o.toString()));
//        mutableLiveData.postValue("Android进阶之光");3


//        MutableLiveData<String> mutableLiveData1;
//        MutableLiveData<String> mutableLiveData2;
//        MutableLiveData<Boolean> liveDataSwitch;
//        mutableLiveData1 = new MutableLiveData<>();
//        mutableLiveData2 = new MutableLiveData<>();
//        liveDataSwitch = new MutableLiveData<>();//1
//
//        LiveData<String> transformedLiveData= Transformations.switchMap(liveDataSwitch, input -> {
//            if (input) {
//                return mutableLiveData1;
//            } else {
//                return mutableLiveData2;
//            }
//        });
//
//        transformedLiveData.observe(this, s -> Log.d(TAG, "onChanged:" + s));
//        liveDataSwitch.postValue(true);//2
//        mutableLiveData1.postValue("Android进阶之光");
//        mutableLiveData2.postValue("Android进阶解密");

        MutableLiveData<String> mutableLiveData1  = new MutableLiveData<>();
        MutableLiveData<String> mutableLiveData2  = new MutableLiveData<>();
        MediatorLiveData<String> liveDataMerger = new MediatorLiveData<>();

        liveDataMerger.addSource(mutableLiveData1, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG, "1:"+o.toString());
                liveDataMerger.postValue(o.toString());
            }
        });

        liveDataMerger.addSource(mutableLiveData2, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG, "2:"+o.toString());
                liveDataMerger.postValue(o.toString());
            }
        });
        liveDataMerger.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG, "onChanged:"+o.toString());
            }
        });

        mutableLiveData1.postValue("Android进阶之光1");
        mutableLiveData2.postValue("Android进阶之光2");
        liveDataMerger.postValue("Android进阶之光3");

    }
}
