package com.example.base.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.base.model.BaseModel;
import com.example.base.net.BaseSubscriber;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseViewModel<M extends BaseModel> extends AndroidViewModel {
    /* RX相关注册 */
    /**
     * 一次性容器，可以容纳多个其他一次性用品和提供I/O添加和移除复杂性。
     */
    private CompositeDisposable mCompositeDisposable;
    private ArrayMap<String, Disposable> mDisposableMap;

    protected M model;

    public final MutableLiveData<Intent> startIntent = new MutableLiveData<>();

    protected final MutableLiveData<ArrayList<Object>> messageToContext = new MutableLiveData<>();

    public BaseViewModel(Application application){
        super(application);
        model = setModel();
    }

    /**
     * 设置model
     * @return model
     */
    protected abstract M setModel();

    protected void getData(int code , BaseSubscriber subscriber , Object... param){
        if (model == null){
            throw new NullPointerException("进行获取数据的操作前，检查Model是否赋值");
        }
        model.getData(code,subscriber,param);
    }

    protected void sendMessageToContext(ArrayList<Object> params){
        messageToContext.setValue(params);
    }

    /**
     * 注册RX可处理的线程
     * @param disposable 可处理的线程
     * @param requestKey 请求键
     */
    public void subscribe(Disposable disposable, @Nullable String requestKey) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);

        if (null == mDisposableMap){
            mDisposableMap = new ArrayMap<>();
        }
        if (!TextUtils.isEmpty(requestKey)){
            mDisposableMap.put(requestKey, disposable);
        }
    }

    /**
     * 注销所有线程
     */
    public void unsubscribe() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 移除线程
     * @param requestKey 请求键
     */
    public void removeDisposable(@NotNull String requestKey) {
        if (null != mCompositeDisposable && null != mDisposableMap
                && mDisposableMap.containsKey(requestKey)) {
            mCompositeDisposable.remove(Objects.requireNonNull(mDisposableMap.get(requestKey)));
            mDisposableMap.remove(requestKey);
        }
    }

    /**
     * 请求时客户端错误处理
     */
    @CallSuper
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        // 当在主线程时显示异常Toast
        if (Looper.getMainLooper() == Looper.myLooper()) {
            //TODO 后面添加
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
