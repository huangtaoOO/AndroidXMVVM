package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aidlclient.databinding.ActivityMainBinding;
import com.example.aidlclient.vm.ViewModelOfMain;
import com.example.aidlcommon.ITaoAidlInterface;
import com.example.common.view.BaseLoadingActivity;
import com.example.common.view.DataBindingConfig;

public class MainActivity extends BaseLoadingActivity<ActivityMainBinding, ViewModelOfMain>
        implements View.OnClickListener{

    private ITaoAidlInterface aidlInterface;
    private ServiceConnection connection;

    @Override
    protected Class<ViewModelOfMain> initViewModel() {
        return ViewModelOfMain.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int vmVariableId() {
        return BR.viewModel;
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mViewModel = getActivityViewModel(initViewModel());
        DataBindingConfig config = new DataBindingConfig(getLayoutId(),vmVariableId(),mViewModel);
        config.addBindingParam(BR.click,this);
        return config;
    }

    @Override
    protected void initUI() {

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.button1:
                    associatedServices();
                    break;
                case R.id.button2:
                    if (aidlInterface!=null)
                        Toast.makeText(this,String.valueOf(aidlInterface.getInt()),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button3:
                    if (aidlInterface!=null)
                        Toast.makeText(this,String.valueOf(aidlInterface.getFloat()),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button4:
                    if (aidlInterface!=null)
                        Toast.makeText(this,String.valueOf(aidlInterface.getBoolean()),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button5:
                    if (aidlInterface!=null)
                        Toast.makeText(this,aidlInterface.getString(),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button6:
                    if (aidlInterface!=null)
                        Toast.makeText(this,String.valueOf(aidlInterface.getDouble()),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button7:
                    if (aidlInterface!=null)
                        Toast.makeText(this, aidlInterface.getBean().toString(),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button8:
                    if (aidlInterface!=null)
                        aidlInterface.showLog();
                    break;
            }
        }catch (RemoteException e){
            Log.e(this.getClass().getSimpleName(),e.getMessage()==null?"":e.getMessage());
        }
    }

    private void associatedServices() {
        if (mViewModel.isBind.getValue() == null)return;
        if (!mViewModel.isBind.getValue()){
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.example.aidlservice",
                    "com.example.aidlservice.AidlTaoService"));
            bindService(intent,connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    aidlInterface =  ITaoAidlInterface.Stub.asInterface(service);
                    mViewModel.isBind.setValue(true);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            },BIND_AUTO_CREATE);
        }else {
            unbindService(connection);
            aidlInterface = null;
            mViewModel.isBind.setValue(false);
        }
    }
}
