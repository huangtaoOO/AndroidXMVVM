package com.example.common.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.BaseApplication;
import com.example.common.utils.NetworkUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author: tao
 * @time: 2019/6/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {
    protected NetworkStatusCallback callback;

    public BaseSubscriber(@NonNull NetworkStatusCallback callback) {
        this(callback, null);
    }

    public BaseSubscriber(@NonNull NetworkStatusCallback callback, @Nullable String requestKey) {
        this.callback = callback;
        this.callback.subscribe(this, requestKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkUtils.hasNetwork(BaseApplication.getInstance())){
            onStartRequest();
        }else {
            onStartOffline();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 请求失败
     * 可重写复用
     */
    @Override
    public void onError(Throwable t) {
        callback.onError(t);
    }

    /**
     * 完成时回调
     */
    @Override
    public void onComplete(){
        callback.onEndRequest();
    }

    /**
     * 当开始请求时没有网络
     * 可重写复用
     */
    public void onStartOffline(){
        callback.onStartOffline();
    }

    /**
     * 当开始请求时时
     * 可重写复用
     */
    public void onStartRequest(){
        callback.onStartRequest();
    }

    /**
     * 当成功返回时
     * @param t 返回的数据
     */
    public abstract void onSuccess(T t);
}
