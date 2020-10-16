package com.tao.androidx_mvvm.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.common.bean.ActionIntentBean;
import com.example.common.bean.MessageEvens;
import com.example.common.view.BaseLoadingFragment;
import com.example.common.view.DataBindingConfig;
import com.tao.androidx_mvvm.BR;
import com.tao.androidx_mvvm.R;
import com.tao.androidx_mvvm.databinding.FragmentLoginBinding;
import com.tao.androidx_mvvm.view_model.ViewModelOfLoginFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseLoadingFragment<FragmentLoginBinding, ViewModelOfLoginFragment>
        implements View.OnClickListener  {

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected int vmVariableId() {
        return BR.viewModel;
    }

    @Override
    protected Class<ViewModelOfLoginFragment> initViewModel() {
        return ViewModelOfLoginFragment.class;
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

    }
}