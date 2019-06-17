package com.tao.androidx_mvvm.basis;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.tao.androidx_mvvm.basis.activity.BaseMvvmActivity;
import com.tao.androidx_mvvm.bean.MessageEvens;
import com.tao.androidx_mvvm.utils.LogUtil;

import java.util.Stack;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    public static final String MESSAGE = "message";

    private Stack<Activity> activities;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        registerCallbacks();
        handler = new Handler(msg -> {
            if (! (msg.obj instanceof Integer)){
                LogUtil.e("BaseApplication Handler","参数错误 msg.obj 需要为Integer");
            }
            for (Activity activity: activities) {
                if (activity instanceof BaseMvvmActivity &&
                        ((BaseMvvmActivity) activity).getTag() == (int) msg.obj){
                    ((BaseMvvmActivity) activity).dispatchMessage(msg.getData().get(MESSAGE));
                    break;
                }
            }
            return true;
        });
    }

    /**
     * 发送一个消息到其创建的activity
     * @param message 消息实体
     */
    public void sendMessage(MessageEvens message){
        Message msg = handler.obtainMessage() ;
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
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activities == null){
                    activities = new Stack<>();
                }
                activities.add( activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
            }
        });
    }
}
