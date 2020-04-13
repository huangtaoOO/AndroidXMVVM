package com.tao.androidx_mvvm.view.activity

import com.tao.androidx_mvvm.R
import com.tao.androidx_mvvm.basis.activity.BaseMvvmActivity
import com.tao.androidx_mvvm.databinding.ActivitySqlBinding
import com.tao.androidx_mvvm.viewmodel.ViewModelOfSQL

class SQLActivity : BaseMvvmActivity<ViewModelOfSQL, ActivitySqlBinding>() {

    override fun initUI() {
        viewModel.setContext(this)
    }

    override fun setViewModel(): Class<ViewModelOfSQL> {
        return ViewModelOfSQL::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_sql
    }

    override fun handlerMsg(`object`: Any?) {

    }
}
