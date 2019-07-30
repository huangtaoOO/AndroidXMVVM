package com.tao.androidx_mvvm.viewmodel

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.tao.androidx_mvvm.MainActivity
import com.tao.androidx_mvvm.basis.net.BaseSubscriber
import com.tao.androidx_mvvm.basis.viewmodel.BaseViewModel
import com.tao.androidx_mvvm.bean.ResponseBean
import com.tao.androidx_mvvm.bean.UserBean
import com.tao.androidx_mvvm.model.ModelOfLogin
import com.tao.androidx_mvvm.model.ModelOfLogin.LOGIN_CODE
import com.tao.androidx_mvvm.utils.ToastUtils

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
class ViewModelOfLogin(application: Application) :BaseViewModel<ModelOfLogin>(application) {

    val edName = MutableLiveData<CharSequence>()

    val edPass = MutableLiveData<CharSequence>()

    override fun setModel(): ModelOfLogin {
        return ModelOfLogin()
    }

    fun onClick(view: View){
        login()
    }

    private fun login(){
        model.getData(LOGIN_CODE,object: BaseSubscriber<ResponseBean<UserBean>>(this) {
            override fun onSuccess(t: ResponseBean<UserBean>) {
                if (t.errorCode == 0 ){
                    jumMainActivity()
                }else{
                    ToastUtils.showTextToast(t.errorMsg)
                }
            }

        },edName.value.toString(),edPass.value.toString())
    }

    private fun jumMainActivity() {
        val intent = Intent(getApplication(), MainActivity::class.java)
        intent.putExtra("needFinish",true)
        startIntent.value = intent
    }
}