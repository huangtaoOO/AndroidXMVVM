package com.tao.androidx_mvvm.view_model;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.common.viewmodel.RxJavaViewModel;

/**
 * @author: tao
 * @time: 2020/9/3
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewModelOfLoginFragment extends RxJavaViewModel {

    public final MutableLiveData<CharSequence> smsDate = new MutableLiveData<>();

    public ViewModelOfLoginFragment(Application application) {
        super(application);
    }

    public void init(){
        Uri uri=Uri.parse("content://sms");
        ContentResolver resolver=getApplication().getContentResolver();
        Cursor cursor=resolver.query(uri,new String[]{"_id","address","body"},null,
                null,null);
        StringBuilder stringBuilder = new StringBuilder();
        if (cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(0);
                String address = cursor.getString(1);
                String body = cursor.getString(2);
                stringBuilder.append(_id);
                stringBuilder.append("\n");
                stringBuilder.append(address);
                stringBuilder.append("\n");
                stringBuilder.append(body);
                stringBuilder.append("\n");
                stringBuilder.append("\n");
            }
            cursor.close();
        }
        smsDate.setValue(stringBuilder.toString());
    }
}
