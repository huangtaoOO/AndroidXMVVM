package com.example.common.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.common.network.NetworkStatusCallback;
import com.example.common.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: tao
 * @time: 2020/9/3
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain:
 */
public abstract class RxJavaViewModel extends BaseViewModel implements NetworkStatusCallback {

    /**Rx 注册器相关*/
    private CompositeDisposable mCompositeDisposable;
    private ArrayMap<String, Disposable> mDisposableMap;

    public final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public RxJavaViewModel(Application application) {
        super(application);
    }

    @Override
    public void onStartRequest() {
        loading.postValue(true);
    }

    @Override
    public void onStartOffline() {
        loading.postValue(false);
    }

    @Override
    public void onEndRequest() {
        loading.postValue(false);
    }

    @Override
    public void onError(Throwable t) {
        loading.postValue(false);
        if (t.getMessage() == null)
            ToastUtils.showTextToast("--出错啦--");
        else
            ToastUtils.showTextToast(t.getMessage());
    }

    /**
     * 注册RX可处理的线程
     * @param disposable 可处理的线程
     * @param requestKey 请求键
     */
    @Override
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
     * 移除线程
     * @param requestKey 请求键
     */
    @Override
    public void removeDisposable(@NotNull String requestKey) {
        if (null != mCompositeDisposable && null != mDisposableMap
                && mDisposableMap.containsKey(requestKey)) {
            mCompositeDisposable.remove(Objects.requireNonNull(mDisposableMap.get(requestKey)));
            mDisposableMap.remove(requestKey);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mDisposableMap!=null){
            mDisposableMap.clear();
        }
        if (mCompositeDisposable!=null){
            mCompositeDisposable.dispose();
        }
    }
}
