package com.example.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.common.bean.MessageEvens;
import com.example.common.utils.LogUtil;
import com.example.common.view.BaseActivity;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

import static com.example.common.bean.MessageEvens.PROMPTLY;
import static com.example.common.bean.MessageEvens.SHOW;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class BaseApplication extends Application  implements ViewModelStoreOwner {

    private static BaseApplication mInstance;

    public static final String MESSAGE = "message";

    private Stack<Activity> activities;
    private Handler handler;
    private ViewModelStore mAppViewModelStore;

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAppViewModelStore = new ViewModelStore();
        registerCallbacks();
        handler = new Handler(msg -> {
            if (! (msg.obj instanceof Integer)){
                LogUtil.e("BaseApplication Handler","参数错误 msg.obj 需要为Integer");
            }
            for (Activity activity: activities) {
                if (activity instanceof BaseActivity &&
                        ((BaseActivity) activity).getTag() == (int) msg.obj){
                    ((BaseActivity) activity).dispatchMessage(msg.getData().get(MESSAGE));
                    break;
                }
            }
            return true;
        });
    }

    /**
     * 发送一个消息到目标activity
     * @param targetTag 目标tag
     * @param message 消息实体
     */
    public void sendMessage(int targetTag,MessageEvens<?> message){
        if (message.getType() != PROMPTLY && message.getType() != SHOW ){
            LogUtil.e("BaseApplication Handler","MessageEvens type 只能为 PROMPTLY | SHOW ");
            return;
        }
        Message msg = handler.obtainMessage() ;
        msg.obj = targetTag;
        Bundle bundle = new Bundle();
        bundle.putSerializable(MESSAGE,message);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    private void registerCallbacks(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NotNull Activity activity, Bundle savedInstanceState) {
                if (activities == null){
                    activities = new Stack<>();
                }
                activities.add( activity);
            }

            @Override
            public void onActivityStarted(@NotNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NotNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NotNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NotNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NotNull Activity activity) {
                activities.remove(activity);
            }
        });
    }
}
