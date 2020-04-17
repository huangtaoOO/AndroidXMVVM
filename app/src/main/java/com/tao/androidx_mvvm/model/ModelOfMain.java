package com.tao.androidx_mvvm.model;

import com.example.base.model.BaseModel;
import com.example.base.net.BaseSubscriber;
import com.example.base.utils.LogUtil;
import com.example.network.NetworkClient;
import com.tao.androidx_mvvm.net.api.UserApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ModelOfMain extends BaseModel {

    public static final int TEXT = 1001;

    @Override
    protected void requestInNet(int code, BaseSubscriber subscriber, Object... parameter) {
        switch (code) {
            case TEXT:
                LogUtil.i("ModelOfMain", "requestInNet");
                NetworkClient.getNetworkClient().createApi(UserApi.class)
                        .wxarticle()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
                break;
            default:
                LogUtil.i("ModelOfMain", "default");
                break;
        }
    }

}
