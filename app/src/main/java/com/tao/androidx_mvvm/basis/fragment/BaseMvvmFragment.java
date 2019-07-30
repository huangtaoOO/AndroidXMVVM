package com.tao.androidx_mvvm.basis.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import com.tao.androidx_mvvm.basis.viewmodel.BaseViewModel;

/**
 * @author: tao
 * @time: 2019/7/30
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseMvvmFragment<VM extends BaseViewModel,VDB extends ViewDataBinding> extends Fragment {

    protected VDB binding;

    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = setViewModel();
        doBeforeBinding();
        onBinding(inflater,container);
        setDefaultObservers();
        return binding.getRoot();
    }

    protected abstract void initUI(LayoutInflater inflater, ViewGroup container);

    protected abstract int getLayoutId();

    protected void doBeforeBinding(){}

    protected void setDefaultObservers(){

    }

    protected void onBinding(LayoutInflater inflater ,ViewGroup container){
        binding = DataBindingUtil.inflate(inflater,
                getLayoutId(),container,false);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel,viewModel);
        initUI(inflater,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract VM setViewModel();
}
