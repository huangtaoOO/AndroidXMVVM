package com.example.common.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.common.BaseApplication;
import com.example.common.R;
import com.example.common.bean.ActionIntentBean;
import com.example.common.bean.MessageEvens;
import com.example.common.viewmodel.BaseViewModel;

/**
 * @Author: tao
 * @ClassName: BaseFragment
 * @Time: 2020/8/29 9:30
 * @E-mail: 1462320178@qq.com
 * @version: 1.0
 * @Description: java类作用描述
 * @Exception: 无
 */
public abstract class BaseDataBingFragment<VDB extends ViewDataBinding,VM extends BaseViewModel> extends Fragment {

    private static final Handler HANDLER = new Handler();
    protected AppCompatActivity mActivity;
    protected boolean mAnimationLoaded;
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider.Factory mFactory;
    protected VDB mBinding;
    protected VM mViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    /**
     * 获取布局文件
     * @return 布局文件ID
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 获取viewModel的变量名
     * @return BR.viewModel
     */
    protected abstract int vmVariableId();

    /**
     * 获取ViewModel的Class
     * @return class
     */
    protected abstract Class<VM> initViewModel();

    /**
     * 组装成DataBindingConfig
     * @return DataBindingConfig
     */
    protected DataBindingConfig getDataBindingConfig(){
        return new DataBindingConfig(getLayoutId(),vmVariableId(),mViewModel = getFragmentViewModel(initViewModel()));
    }

    protected void onBinding(LayoutInflater inflater, ViewGroup container){
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        VDB binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        binding.setLifecycleOwner(this);
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray<?> bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        doBeforeBinding();
        onBinding(inflater,container);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        setDefaultObservers();
    }

    protected void setDefaultObservers(){
        mViewModel.getActionIntent().observe(getViewLifecycleOwner(),it -> {
            if (it == null) return;
            handleActionForViewModel(it);
        });
        mViewModel.getMessageToContext().observe(getViewLifecycleOwner(),it -> {
            if (it == null) return;
            handleMessageForViewModel(it);
        });
    }


    protected abstract void handleMessageForViewModel(@NonNull MessageEvens<?> it);

    protected abstract void handleActionForViewModel(@NonNull ActionIntentBean it);

    protected abstract void initUI();

    protected void doBeforeBinding(){

    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //TODO 错开动画转场与 UI 刷新的时机，避免掉帧卡顿的现象
        HANDLER.postDelayed(() -> {
            if (!mAnimationLoaded) {
                mAnimationLoaded = true;
                loadInitData();
            }
        }, 280);
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    protected void loadInitData() {

    }

    public boolean isDebug() {
        return mActivity.getApplicationContext().getApplicationInfo() != null &&
                (mActivity.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    protected void showLongToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int stringRes) {
        showLongToast(mActivity.getApplicationContext().getString(stringRes));
    }

    protected void showShortToast(int stringRes) {
        showShortToast(mActivity.getApplicationContext().getString(stringRes));
    }

    protected <T extends ViewModel> T getFragmentViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this,getAppFactory(mActivity));
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity,getAppFactory(mActivity));
        }
        return mActivityProvider.get(modelClass);
    }

    protected ViewModelProvider getAppViewModelProvider() {
        return new ViewModelProvider((BaseApplication) mActivity.getApplicationContext(),
                getAppFactory(mActivity));
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        checkActivity(this);
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

    private void checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
        }
    }

}
