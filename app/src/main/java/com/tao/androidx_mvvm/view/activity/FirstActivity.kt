package com.tao.androidx_mvvm.view.activity

import com.tao.androidx_mvvm.R
import com.tao.androidx_mvvm.basis.activity.BaseMvvmActivity
import com.tao.androidx_mvvm.databinding.ActivityFirstBinding
import com.tao.androidx_mvvm.viewmodel.ViewModelOfFirst

class FirstActivity : BaseMvvmActivity<ViewModelOfFirst, ActivityFirstBinding>() {
    override fun initUI() {
       viewModel.startCountDown()
    }

    override fun setViewModel(): ViewModelOfFirst {
        return ViewModelOfFirst(application)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_first
    }

    override fun handlerMsg(`object`: Any?) {

    }
}
