package com.example.common.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.common.bean.ActionIntentBean;
import com.example.common.bean.MessageEvens;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseViewModel extends AndroidViewModel {

    protected final MutableLiveData<ActionIntentBean> actionIntent = new MutableLiveData<>();

    protected final MutableLiveData<MessageEvens<?>> messageToContext = new MutableLiveData<>();

    public BaseViewModel(Application application){
        super(application);
    }

    protected void setActionIntent(ActionIntentBean bean){
        actionIntent.setValue(bean);
    }

    protected void setMessageToContext(MessageEvens<?> evens){
        messageToContext.setValue(evens);
    }

    public MutableLiveData<ActionIntentBean> getActionIntent() {
        return actionIntent;
    }

    public MutableLiveData<MessageEvens<?>> getMessageToContext() {
        return messageToContext;
    }
}
