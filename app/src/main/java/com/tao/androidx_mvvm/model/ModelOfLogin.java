package com.tao.androidx_mvvm.model;

import com.tao.androidx_mvvm.basis.model.BaseModel;
import com.tao.androidx_mvvm.basis.net.BaseSubscriber;
import com.tao.androidx_mvvm.bean.User;
import com.tao.androidx_mvvm.dao.UserDao;
import com.tao.androidx_mvvm.net.NetworkClient;
import com.tao.androidx_mvvm.net.api.UserApi;
import com.tao.androidx_mvvm.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ModelOfLogin extends BaseModel {

    public static final int LOGIN_CODE = 1001;

    @Override
    protected void requestInNet(int code, BaseSubscriber subscriber, Object... parameter) {
        switch (code) {
            case LOGIN_CODE:
                if (parameter == null || parameter[0] == null || parameter[1] == null){
                    LogUtil.e("ModelOfLogin","参数异常: LOGIN_CODE ");
                    return;
                }
                NetworkClient.getNetworkClient().createApi(UserApi.class)
                        .login((String) parameter[0],(String) parameter[1])
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
                break;
            default:
                LogUtil.i("ModelOfMain", "default");
                break;
        }
    }

    public User getUserInformationLogged(){
        return UserDao.getUserForm();
    }
}
