package com.tao.androidx_mvvm.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.common.bean.ActionIntentBean;
import com.example.common.bean.MessageEvens;
import com.example.common.view.BaseLoadingFragment;
import com.example.common.view.DataBindingConfig;
import com.tao.androidx_mvvm.BR;
import com.tao.androidx_mvvm.R;
import com.tao.androidx_mvvm.databinding.FragmentLoginBinding;
import com.tao.androidx_mvvm.view_model.ViewModelOfLoginFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseLoadingFragment<FragmentLoginBinding, ViewModelOfLoginFragment>
        implements View.OnClickListener  {

    private static final int SMS_PERMISSION = 0xA;

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
        requestPermission();
    }

    @Override
    public void onClick(View v) {

    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_SMS},
                    SMS_PERMISSION);
        }else {
            mViewModel.init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModel.init();
            } else {
                showWaringDialog();
            }
        }
    }

    private void showWaringDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("警告！")
                .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", (dialog, which) -> {
                    // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.action_launchFragment_to_loginFragment);
                }).show();
    }

}