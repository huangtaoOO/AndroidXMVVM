package com.example.common.view;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.common.BaseApplication;
import com.example.common.bean.ActionIntentBean;
import com.example.common.bean.MessageEvens;
import com.example.common.viewmodel.BaseViewModel;

/**
 * @Author: tao
 * @ClassName: BaseDatabingActivity
 * @Time: 2020/8/29 8:44
 * @E-mail: 1462320178@qq.com
 * @version: 1.0
 * @Description: java类作用描述
 * @Exception: 无
 */
public abstract class BaseDataBingActivity<VDB extends ViewDataBinding , VM extends BaseViewModel> extends BaseActivity{

    private ViewModelProvider mActivityProvider;
    private ViewModelProvider.Factory mFactory;
    protected VDB mBinding;
    protected VM mViewModel;

    protected abstract Class<VM> initViewModel();

    protected DataBindingConfig getDataBindingConfig(){
        return new DataBindingConfig(getLayoutId(),vmVariableId(),mViewModel = getActivityViewModel(initViewModel()));
    }

    /**
     * 获取布局文件id
     * @return id
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 获取DataBing绑定的变量名
     * @return BR。viewModel
     */
    protected abstract int vmVariableId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeBinding();
        onBinding();
        initUI();
        setDefaultObservers();
    }

    /**
     * 设置观察者
     */
    protected void setDefaultObservers(){
        mViewModel.getActionIntent().observe(this,it -> {
            if (it == null) return;
            handleActionForViewModel(it);
        });
        mViewModel.getMessageToContext().observe(this,it -> {
            if (it == null) return;
            handleMessageForViewModel(it);
        });
    }

    /**
     * 处理来自ViewModel的消息
     * 一般是界面变化啥的
     * @param it 消息
     */
    protected abstract void handleMessageForViewModel(@NonNull MessageEvens<?> it);

    /**
     * 处理来自ViewModel的动作
     * 一般是启动界面啥的
     * @param it 消息
     */
    protected abstract void handleActionForViewModel(@NonNull ActionIntentBean it);

    /**
     * 初始化UI
     */
    protected abstract void initUI();

    /**
     * 绑定界面
     */
    protected void onBinding(){
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        VDB binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray<?> bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
    }

    /**
     * 绑定BataBing之前
     */
    protected void doBeforeBinding() { }

    /**
     * 是否是debug状态
     * @return debug 状态
     */
    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 显示长时间Toast
     * @param text 文本
     */
    protected void showLongToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示短时间Toast
     * @param text 文本
     */
    protected void showShortToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长时间Toast
     * @param stringRes 资源id
     */
    protected void showLongToast(int stringRes) {
        showLongToast(getApplicationContext().getString(stringRes));
    }

    /**
     * 显示短时间Toast
     * @param stringRes 资源id
     */
    protected void showShortToast(int stringRes) {
        showShortToast(getApplicationContext().getString(stringRes));
    }

    /**
     * 获取activity的viewModel
     * @param modelClass class
     * @param <T> viewModel的泛型
     * @return viewModel
     */
    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this,getAppFactory(this));
        }
        return mActivityProvider.get(modelClass);
    }

    protected ViewModelProvider getAppViewModelProvider() {
        return new ViewModelProvider((BaseApplication) this.getApplicationContext(),
                getAppFactory(this));
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        Application application = checkApplication(activity);
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        }
        return mFactory;
    }

    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }

}
