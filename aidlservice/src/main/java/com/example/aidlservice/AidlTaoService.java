package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidlcommon.AidlBean;
import com.example.aidlcommon.ITaoAidlInterface;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class AidlTaoService extends Service {

    public AidlTaoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new TaoBinder(this);
    }

    static class TaoBinder extends ITaoAidlInterface.Stub{
        private SoftReference<AidlTaoService> reference;

        public TaoBinder(AidlTaoService service) {
            reference = new SoftReference<>(service);
        }

        @Override
        public int getInt() throws RemoteException {
            return 520;
        }

        @Override
        public int getLong() throws RemoteException {
            return 520*1314;
        }

        @Override
        public boolean getBoolean() throws RemoteException {
            return true;
        }

        @Override
        public float getFloat() throws RemoteException {
            return 0.1314F;
        }

        @Override
        public double getDouble() throws RemoteException {
            return 0.1314D;
        }

        @Override
        public String getString() throws RemoteException {
            return "AIDL";
        }

        @Override
        public AidlBean getBean() throws RemoteException {
            AidlBean bean = new AidlBean();
            List<String> list = new ArrayList<>(2);
            list.add("小明");
            list.add("小黄");
            bean.setList(list);
            bean.setString("这个是一个悲伤的故事");
            return bean;
        }

        @Override
        public void showLog() throws RemoteException {
            if (reference.get()!=null){
                Log.e(this.getClass().getSimpleName(),"showLog from = " + reference.get().getClass().getSimpleName());
            }
        }
    }
}
