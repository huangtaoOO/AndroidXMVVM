package com.tao.androidx_mvvm.viewmodel;

import android.app.Application;
import android.view.View;

import com.example.base.viewmodel.BaseViewModel;
import com.tao.androidx_mvvm.model.ModelOfMain;



/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewModelOfMain extends BaseViewModel<ModelOfMain> {

    //public final MutableLiveData<String> text = new MutableLiveData<>();

    public ViewModelOfMain(Application application) {
        super(application);
       // text.setValue("Hello");
    }

    public void getData(){
//        getData(TEXT, new BaseSubscriber(this) {
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onSuccess(Object o) {
//                LogUtil.i("ViewModelOfMain",o.toString());
//                text.setValue(o.toString());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                super.onError(t);
//                LogUtil.i("ViewModelOfMain",t.getMessage());
//            }
//        });
    }

    @Override
    protected ModelOfMain setModel() {
        return new ModelOfMain();
    }

    public void onClick(View v){
//        if (v.getId() == R.id.text){
//           getData();
//        }
    }
}
