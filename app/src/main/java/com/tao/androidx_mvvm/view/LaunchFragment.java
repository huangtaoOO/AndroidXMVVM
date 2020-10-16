package com.tao.androidx_mvvm.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.common.bean.ActionIntentBean;
import com.example.common.bean.MessageEvens;
import com.example.common.view.BaseLoadingFragment;
import com.example.common.view.DataBindingConfig;
import com.tao.androidx_mvvm.BR;
import com.tao.androidx_mvvm.R;
import com.tao.androidx_mvvm.databinding.FragmentLauncherBinding;
import com.tao.androidx_mvvm.view_model.ViewModelOfLaunchFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchFragment extends BaseLoadingFragment<FragmentLauncherBinding, ViewModelOfLaunchFragment>
        implements View.OnClickListener {

    public static LaunchFragment newInstance() {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_launcher;
    }

    @Override
    protected int vmVariableId() {
        return BR.viewModel;
    }

    @Override
    protected Class<ViewModelOfLaunchFragment> initViewModel() {
        return ViewModelOfLaunchFragment.class;
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mViewModel = getFragmentViewModel(initViewModel());
        DataBindingConfig config = new DataBindingConfig(getLayoutId(),vmVariableId(), mViewModel);
        config.addBindingParam(BR.click,this);
        return config;
    }

    @Override
    protected void handleMessageForViewModel(@NonNull MessageEvens<?> it) {

    }

    @Override
    protected void handleActionForViewModel(@NonNull ActionIntentBean it) {

    }

    @Override
    protected void initUI() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_login:
                Navigation.findNavController(mBinding.getRoot()).navigate(R.id.action_launchFragment_to_loginFragment);
                break;
            case R.id.btn_forget:
                break;
        }
    }
}