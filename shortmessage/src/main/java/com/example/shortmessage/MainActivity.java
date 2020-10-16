package com.example.shortmessage;

import android.view.View;

import com.example.common.view.BaseLoadingActivity;
import com.example.common.view.DataBindingConfig;
import com.example.shortmessage.databinding.ActivityMainBinding;
import com.example.shortmessage.vm.ViewModelOfMain;
import com.plattysoft.leonids.ParticleSystem;

public class MainActivity extends BaseLoadingActivity<ActivityMainBinding, ViewModelOfMain>
        implements View.OnClickListener {

    @Override
    protected Class<ViewModelOfMain> initViewModel() {
        return ViewModelOfMain.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int vmVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mViewModel = getActivityViewModel(initViewModel());
        DataBindingConfig config = new DataBindingConfig(getLayoutId(),vmVariableId(), mViewModel);
        config.addBindingParam(BR.click,this);
        return config;
    }

    @Override
    public void onClick(View v) {
        new ParticleSystem(this, 1000, R.mipmap.text01, 3000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 360)
                .setRotationSpeed(30)
                .setAcceleration(0, 90)
                .oneShot(v, 200);
    }
}
