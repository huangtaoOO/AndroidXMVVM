package com.tao.androidx_mvvm.basis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import com.tao.androidx_mvvm.BR;

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseMvvmFragment<VM extends AndroidViewModel ,VDB extends ViewDataBinding> extends Fragment {

    protected VM viewModel;
    
    protected VDB binding ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel,viewModel);
        initUI(inflater,container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void initUI(LayoutInflater inflater, ViewGroup container);

    protected abstract int getLayoutId();

    protected abstract void initData();



}
