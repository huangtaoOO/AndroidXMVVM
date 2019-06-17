package com.tao.androidx_mvvm.basis.net;

import androidx.annotation.Nullable;
import com.tao.androidx_mvvm.basis.viewmodel.BaseViewModel;
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
    protected BaseViewModel vm;

    public BaseSubscriber(BaseViewModel vm) {
        this(vm, null);
    }
    public BaseSubscriber(BaseViewModel vm, @Nullable String requestKey) {
        this.vm = vm;
        this.vm.subscribe(this, requestKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        /// TODO 暂时注释
//        if (!NetworkUtils.hasNetwork(BaseApplication.getInstance())) {
//            onStartOffline();
//            if (!isDisposed()){
//                this.dispose();
//            }
//        } else {
//            onStartRequest();
//        }
        onStartRequest();
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
        vm.onError(t);
    }

    /**
     * 完成时回调
     */
    @Override
    public abstract void onComplete();

    /**
     * 当开始请求时没有网络
     * 可重写复用
     */
    public void onStartOffline(){

    }

    /**
     * 当开始请求时时
     * 可重写复用
     */
    public void onStartRequest(){

    }

    /**
     * 当成功返回时
     * @param t 返回的数据
     */
    public abstract void onSuccess(T t);
}
