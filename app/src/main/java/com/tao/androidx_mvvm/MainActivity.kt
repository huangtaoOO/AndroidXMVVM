package com.tao.androidx_mvvm

import com.tao.androidx_mvvm.basis.activity.BaseMvvmActivity
import com.tao.androidx_mvvm.databinding.ActivityMainBinding
import com.tao.androidx_mvvm.viewmodel.ViewModelOfMain

class MainActivity : BaseMvvmActivity<ViewModelOfMain,ActivityMainBinding>() {

    /****
     *
    INSERT INTO `tb_region`(`code`, `name`, `level`, `remark`, `superior_region_id`, `superior_region_name`, `country`) VALUES (111201, '宋庄镇', 3, NULL, 111200, '通州区', 86);
     */

    val text1 = "INSERT INTO `tb_region`(`code`, `name`, `level`, `remark`, `superior_region_id`, `superior_region_name`, `country`) VALUES ("
    val text2 = ", '"
    val text3 = "', 3, NULL, "
    val text4 = ", '"
    val text5 = ", 86);"

    override fun setViewModel(): ViewModelOfMain {
        return ViewModelOfMain(application)
    }

    override fun initUI() {

    }

    override fun handlerMsg(`object`: Any?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}
