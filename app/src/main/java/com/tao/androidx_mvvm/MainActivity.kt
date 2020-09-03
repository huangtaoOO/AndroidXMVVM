package com.tao.androidx_mvvm

import android.view.View
import com.example.common.bean.ActionIntentBean
import com.example.common.bean.MessageEvens
import com.example.common.view.BaseLoadingActivity
import com.example.common.view.DataBindingConfig
import com.tao.androidx_mvvm.databinding.ActivityMainBinding
import com.tao.androidx_mvvm.view_model.ViewModelOfMain

class MainActivity : BaseLoadingActivity<ActivityMainBinding, ViewModelOfMain>(),View.OnClickListener {

    override fun initViewModel(): Class<ViewModelOfMain> {
        return ViewModelOfMain::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun vmVariableId(): Int {
        return BR.viewModel
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        mViewModel = getActivityViewModel(initViewModel())
        val config = DataBindingConfig(layoutId,vmVariableId(),mViewModel)
        config.addBindingParam(BR.click,this)
        return config
    }

    override fun handleMessageForViewModel(it: MessageEvens<*>) {
        TODO("Not yet implemented")
    }

    override fun handlerMsg(`object`: Any?) {
        TODO("Not yet implemented")
    }

    override fun handleActionForViewModel(it: ActionIntentBean) {
        TODO("Not yet implemented")
    }

    override fun initUI() {
        mViewModel.text.value = "MainActivity"

    }

    override fun onClick(v: View?) {
        showLoadDialog()
        mBinding.text.postDelayed({ dismissLoadDialog() },1000)
    }

}
