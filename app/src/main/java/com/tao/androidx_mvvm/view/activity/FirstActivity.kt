package com.tao.androidx_mvvm.view.activity

import com.example.base.activity.BaseMActivity
import com.tao.androidx_mvvm.R
import com.tao.androidx_mvvm.databinding.ActivityFirstBinding
import com.tao.androidx_mvvm.viewmodel.ViewModelOfFirst

class FirstActivity : BaseMActivity<ViewModelOfFirst, ActivityFirstBinding>() {
    override fun initUI() {
        viewModel.startCountDown()
    }

    override fun setViewModel(): Class<ViewModelOfFirst> {
        return ViewModelOfFirst::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_first
    }

    override fun handlerMsg(`object`: Any?) {

    }
}
