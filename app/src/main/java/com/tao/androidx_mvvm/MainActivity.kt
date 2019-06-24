package com.tao.androidx_mvvm

import com.tao.androidx_mvvm.basis.activity.BaseMvvmActivity
import com.tao.androidx_mvvm.databinding.ActivityMainBinding
import com.tao.androidx_mvvm.viewmodel.ViewModelOfMain

class MainActivity : BaseMvvmActivity<ViewModelOfMain,ActivityMainBinding>() {
    override fun setViewModel(): ViewModelOfMain {
        return ViewModelOfMain(application)
    }

    override fun initUI() {
        viewModel.text.value = "http://img5.imgtn.bdimg.com/it/u=3300305952,1328708913&fm=26&gp=0.jpg"
    }

    override fun handlerMsg(`object`: Any?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}
