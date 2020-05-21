package com.example.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.base.BR;
import com.example.base.viewmodel.BaseViewModel;

/**
 * @author: tao
 * @time: 2019/7/30
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseMFragment<VM extends BaseViewModel,VDB extends ViewDataBinding> extends Fragment {

    protected VDB binding;

    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        doBeforeBinding();
        onBinding(inflater,container);
        setDefaultObservers();
        return binding.getRoot();
    }

    /**
     * 初始化控件
     */
    protected abstract void initUI();

    /**
     * 返回控件的布局ID
     * @return id
     */
    protected abstract int getLayoutId();

    /**
     * 绑定之前执行
     */
    protected void doBeforeBinding(){}

    /**
     * 设置默认的观察者
     */
    protected void setDefaultObservers(){

    }

    protected void onBinding(LayoutInflater inflater ,ViewGroup container){
        viewModel = new ViewModelProvider(this).get(setViewModel());
        binding = DataBindingUtil.inflate(inflater,
                getLayoutId(),container,false);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel,viewModel);
        initUI();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 返回对应的viewModel类型
     * @return class
     */
    protected abstract Class<VM> setViewModel();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
