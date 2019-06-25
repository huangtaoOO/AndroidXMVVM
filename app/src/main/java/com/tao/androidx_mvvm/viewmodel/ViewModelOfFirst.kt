package com.tao.androidx_mvvm.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tao.androidx_mvvm.MainActivity
import com.tao.androidx_mvvm.R
import com.tao.androidx_mvvm.basis.viewmodel.BaseViewModel
import com.tao.androidx_mvvm.model.ModelOfFirst
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
class ViewModelOfFirst(application: Application) :BaseViewModel<ModelOfFirst>(application){
    val timeKey = "ViewModelOfFirstTimer"

    val countdown = MutableLiveData<CharSequence>()

    override fun setModel(): ModelOfFirst {
        return ModelOfFirst()
    }

    fun startCountDown(){
        //Observable.interval(0, 1, TimeUnit.SECONDS)
        //参数一：什么时候开始发送
        //参数二：隔多长时间发一次
        //参数三：计时的单位，是秒还是毫秒
        val time = 3
        Observable.interval(
            0,1,TimeUnit.SECONDS)
            .take((time+1).toLong())
            .map(object: Function<Long,Long> {
                override fun apply(t: Long): Long {
                    return time - t
                }
            })
                //观察者 回调的线程
            .observeOn(AndroidSchedulers.mainThread())
                //被观察者 执行耗时操作的线程
            .subscribeOn(Schedulers.newThread())
            .subscribe(object: io.reactivex.Observer<Long?> {
                override fun onComplete() {
                    removeDisposable(timeKey)
                }

                override fun onSubscribe(d: Disposable) {
                    subscribe(d,timeKey)
                }

                override fun onNext(t: Long) {
                    countdown.value = getApplication<Application>().getString(R.string.first_jump, t.toString())
                    if (t == 0L){
                        startIntent.value = Intent(getApplication(),MainActivity::class.java)
                    }
                }

                override fun onError(e: Throwable) {
                    removeDisposable(timeKey)
                }
            })

    }

    fun onClick(view:View){
        startIntent.value = Intent(getApplication(),MainActivity::class.java)
    }

}