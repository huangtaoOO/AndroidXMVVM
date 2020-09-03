package com.example.common.network;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

/**
 * @author: tao
 * @time: 2020/9/3
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public interface NetworkStatusCallback {
    /**
     * 请求开始
     */
    void onStartRequest();

    /**
     * 无网络
     */
    void onStartOffline();

    /**
     * 请求结束
     */
    void onEndRequest();

    /**
     * 代码出错
     * @param t Throwable
     */
    void onError(Throwable t);

    /**
     * 注册回调
     * @param disposable 注册
     * @param requestKey 请求键 可为null
     */
    void subscribe(Disposable disposable, @Nullable String requestKey);

    /**
     * 移除回调
     * @param requestKey 请求键
     */
    void removeDisposable(@NotNull String requestKey);
}
