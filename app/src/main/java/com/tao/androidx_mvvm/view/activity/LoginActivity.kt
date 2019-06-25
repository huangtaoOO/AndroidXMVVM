package com.tao.androidx_mvvm.view.activity

import com.tao.androidx_mvvm.R
import com.tao.androidx_mvvm.basis.activity.BaseMvvmActivity
import com.tao.androidx_mvvm.databinding.ActivityLoginBinding
import com.tao.androidx_mvvm.viewmodel.ViewModelOfLogin

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
class LoginActivity : BaseMvvmActivity<ViewModelOfLogin, ActivityLoginBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun setViewModel(): ViewModelOfLogin {
        return ViewModelOfLogin(application)
    }

    override fun initUI() {

    }

    override fun handlerMsg(`object`: Any?) {

    }

}